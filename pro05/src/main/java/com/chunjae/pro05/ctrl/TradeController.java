package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.TradeCategoryService;
import com.chunjae.pro05.biz.TradeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.*;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/trade/*")
public class TradeController {

    @Autowired
    private TradeCategoryService tradeCategoryService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    public String tradeList(HttpServletRequest request, Model model) throws Exception {
        // 위치 정보 처리도 해야 함~
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cno");
        Page page = new Page(curPage, type, keyword, category);
        page.setLocation(request.getParameter("location"));
        page.makePage(tradeService.totalCnt(page));

        List<TradeVO> tradeList = tradeService.tradeList(page);
        model.addAttribute("tradeList", tradeList);

        List<TradeCategory> tradeCategories = tradeCategoryService.tradeCategoryList();
        model.addAttribute("tradeCategories", tradeCategories);

        model.addAttribute("page", page);
        return "trade/tradeList";
    }

    @GetMapping("detail.do")
    public String tradeDetail(@RequestParam int tno, Model model) throws Exception {
        TradeVO trade = tradeService.getTradeVO(tno);
        model.addAttribute("trade", trade);

        UserRating userRating = userService.getUserRating(trade.getName());
        model.addAttribute("userRating", userRating);

        return "/trade/tradeDetail";
    }

    @PostMapping("plusRecommend.do")
    @ResponseBody
    public TradeVO plusRecommend(@RequestBody Trade trade, Principal principal, HttpServletRequest request, HttpSession session) throws Exception {
        Cookie[] cookieFromRequest = request.getCookies();
        String cookieValue = null;
        for(int i=0; i< cookieFromRequest.length; i++) {
            cookieValue = cookieFromRequest[0].getValue();
        }
        // 쿠키 세션 입력
        if(session.getAttribute(trade.getTno() + ":tradeCookie") == null) {
            session.setAttribute(trade.getTno() + ":tradeCookie", trade.getTno() + ":" + cookieValue);
        } else {
            session.setAttribute(trade.getTno() + ":tradeCookie ex", session.getAttribute(trade.getTno() + ":tradeCookie"));
            if(!session.getAttribute(trade.getTno() + ":tradeCookie").equals(trade.getTno() + ":" + cookieValue)) {
                session.setAttribute(trade.getTno() + ":tradeCookie", trade.getTno() + ":" + cookieValue);
            }
        }
        if(!session.getAttribute(trade.getTno() + ":tradeCookie").equals(session.getAttribute(trade.getTno() + ":tradeCookie ex"))) {
            User user = userService.getUserById(Long.valueOf(principal.getName()));
            TradeRecommends tradeRecommends = new TradeRecommends();
            tradeRecommends.setName(user.getName());
            tradeRecommends.setTno(trade.getTno());
            tradeService.updateRecommend(trade.getTno(), "Plus", tradeRecommends);
        }
        TradeVO resTrade = tradeService.getTradeVO(trade.getTno());
        return resTrade;
    }

    @PostMapping("minusRecommend.do")
    @ResponseBody
    public TradeVO minusRecommend(@RequestBody Trade trade, Principal principal, HttpServletRequest request, HttpSession session) throws Exception {
        session.removeAttribute(trade.getTno() + ":tradeCookie");
        session.removeAttribute(trade.getTno() + ":tradeCookie ex");

        User user = userService.getUserById(Long.valueOf(principal.getName()));
        TradeRecommends tradeRecommends = new TradeRecommends();
        tradeRecommends.setName(user.getName());
        tradeRecommends.setTno(trade.getTno());
        tradeService.updateRecommend(trade.getTno(), "Minus", tradeRecommends);

        TradeVO resTrade = tradeService.getTradeVO(trade.getTno());
        return resTrade;
    }

    @GetMapping("insert.do")
    public String tradeInsertForm(Model model) throws Exception {
        List<TradeCategory> tradeCategories = tradeCategoryService.tradeCategoryList();
        model.addAttribute("tradeCategories", tradeCategories);

        return "trade/tradeInsert";
    }

    @PostMapping("insert.do")
    public String tradeInsert(Trade trade, @RequestParam("upfile") MultipartFile file, Principal principal, RedirectAttributes rttr) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));
        trade.setName(user.getName());

        String currentDir = System.getProperty("user.dir");           // 업로드 경로 설정
        String relativePath = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "upload" + File.separator + "items";
        String fileDir = currentDir + relativePath;
        File folder = new File(fileDir);
        if(!folder.exists()) {                                  // 폴더가 존재하지 않으면 폴더 생성
            folder.mkdirs();
        }
        System.out.println(fileDir);

        String originalFileName = file.getOriginalFilename(); // 첨부파일의 실제 파일명
        if (!originalFileName.isEmpty()) {
            String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));     // 파일 이름을 랜덤으로 설정
            trade.setItemImg(saveFileName);
            file.transferTo(new File(folder, saveFileName));    // 파일을 업로드 폴더에 저장
        }

        int result = tradeService.tradeInsert(trade);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "글이 등록되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "글 등록에 실패했습니다. 잠시 후 다시 시도해주세요.");
        }

        return "redirect:list.do";
    }

    @GetMapping("edit.do")
    public String tradeEditForm(@RequestParam int tno, Model model) throws Exception {
        List<TradeCategory> tradeCategories = tradeCategoryService.tradeCategoryList();
        model.addAttribute("tradeCategories", tradeCategories);

        Trade trade = tradeService.getTrade(tno);
        model.addAttribute("trade", trade);

        return "trade/tradeEdit";
    }

    @PostMapping("edit.do")
    public String tradeEdit(Trade trade, @RequestParam("upfile") MultipartFile file, RedirectAttributes rttr) throws Exception {

        // 수정하기 구현 필요

        return "redirect:detail.do?tno=" + trade.getTno();
    }

    @GetMapping("delete.do")
    public String tradeDelete(@RequestParam int tno, RedirectAttributes rttr) throws Exception {
        int result = tradeService.tradeDelete(tno);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "성공적으로 글이 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("msg", "글 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.");
        }
        return "redirect:list.do";
    }

    //ckeditor를 이용한 이미지 업로드
    @PostMapping("imageUpload.do")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiFile, @RequestParam MultipartFile upload) throws Exception{
        // 랜덤 문자 생성
        UUID uid = UUID.randomUUID();

        OutputStream out = null;
        PrintWriter printWriter = null;

        //인코딩
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try{
            //파일 이름 가져오기
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            //이미지 경로 생성
            String rootPath = System.getProperty("user.dir");           // 업로드 경로 설정
            String fileDir = rootPath + "/src/main/resources/static/upload/items/";
            String ckUploadPath = fileDir + uid + "_" + fileName;
            File folder = new File(fileDir);
            System.out.println("path:"+fileDir);	// 이미지 저장경로 console에 확인
            System.out.println("ckUploadPath:"+ckUploadPath);
            //해당 디렉토리 확인
            if(!folder.exists()){
                try{
                    folder.mkdirs(); // 폴더 생성
                }catch(Exception e){
                    e.getStackTrace();
                }
            }

            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화

            printWriter = response.getWriter();
            String contextPath = request.getContextPath();
            String fileUrl = contextPath + "/trade/ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName; // 작성화면

            // 업로드시 메시지 출력
            printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
            printWriter.flush();

        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) { out.close(); }
                if(printWriter != null) { printWriter.close(); }
            } catch(IOException e) { e.printStackTrace(); }
        }
        return;
    }

    //ckeditor를 이용한 서버에 전송된 이미지 뿌려주기
    @RequestMapping(value="ckImgSubmit.do")
    public void ckSubmit(@RequestParam(value="uid") String uid, @RequestParam(value="fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //서버에 저장된 이미지 경로
        String rootPath = System.getProperty("user.dir");           // 업로드 경로 설정
        String fileDir = rootPath + "/src/main/resources/static/upload/items/";
        String sDirPath = fileDir + uid + "_" + fileName;
        System.out.println(sDirPath);

        File imgFile = new File(sDirPath);

        //사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
        if(imgFile.isFile()){
            byte[] buf = new byte[1024];
            int readByte = 0;
            int length = 0;
            byte[] imgBuf = null;

            FileInputStream fileInputStream = null;
            ByteArrayOutputStream outputStream = null;
            ServletOutputStream out = null;

            try{
                fileInputStream = new FileInputStream(imgFile);
                outputStream = new ByteArrayOutputStream();
                out = response.getOutputStream();

                while((readByte = fileInputStream.read(buf)) != -1){
                    outputStream.write(buf, 0, readByte);
                }

                imgBuf = outputStream.toByteArray();
                length = imgBuf.length;
                out.write(imgBuf, 0, length);
                out.flush();

            }catch(IOException e){
                e.printStackTrace();
            }finally {
                outputStream.close();
                fileInputStream.close();
                out.close();
            }
        }
    }

}
