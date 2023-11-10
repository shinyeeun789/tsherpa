package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.FreeCommentService;
import com.chunjae.pro05.biz.FreeService;
import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.Category;
import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.entity.Free;
import com.chunjae.pro05.entity.FreeComment;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/free/*")
public class FreeController {
    @Autowired
    private FreeService freeService;

    @Autowired
    private FreeCommentService freeCommentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    public String freeList(HttpServletRequest request, Model model) throws Exception {
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cate");
        Page page = new Page(curPage, type, keyword, category);
        page.makePage(freeService.totalCnt());

        List<Free> freeList = freeService.freeList(page);
        model.addAttribute("freeList", freeList);

        List<Category> cateList = freeService.cateList();
        model.addAttribute("cateList", cateList);

        model.addAttribute("page", page);
        return "free/freeList";
    }

    @GetMapping("insert.do")
    public String freeInsertForm(Model model) throws Exception {

        List<Category> cateList = freeService.cateList();
        model.addAttribute("cateList", cateList);

        return "free/freeInsert";
    }

    @PostMapping("insert.do")
    public String freeInsert(Free free, HttpServletRequest request, Principal principal, RedirectAttributes rttr) throws Exception {
        User user = userService.getUserById(Long.valueOf(principal.getName()));
        free.setName(user.getName());
        int result = freeService.insertFree(free);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "게시판에 글이 등록되었습니다.");
            return "redirect:list.do";
        } else {
            rttr.addFlashAttribute("msg", "글 등록에 실패했습니다. 잠시 후 다시 시도해주세요.");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
    }

    @GetMapping("detail.do")
    public String freeDetail(@RequestParam int fno, HttpServletRequest request, HttpSession session, Model model) throws Exception {
        Cookie[] cookieFromRequest = request.getCookies();
        String cookieValue = null;
        for(int i=0; i< cookieFromRequest.length; i++) {
            cookieValue = cookieFromRequest[0].getValue();
        }
        // 쿠키 세션 입력
        if(session.getAttribute(fno + ":freeCookie") == null) {
            session.setAttribute(fno + ":freeCookie", fno + ":" + cookieValue);
        } else {
            session.setAttribute(fno + ":freeCookie ex", session.getAttribute(fno + ":freeCookie"));
            if(!session.getAttribute(fno + ":freeCookie").equals(fno + ":" + cookieValue)) {
                session.setAttribute(fno + ":freeCookie", fno + ":" + cookieValue);
            }
        }
        // 쿠키와 세션이 없는 경우 조회수 카운트
        if(!session.getAttribute(fno + ":freeCookie").equals(session.getAttribute(fno + ":freeCookie ex"))) {
            freeService.updateViews(fno);
        }

        Free free = freeService.getFree(fno);
        model.addAttribute("detail", free);

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        Page page = new Page(curPage);
        page.makePage(freeService.totalCnt());
        List<FreeComment> commentList = freeCommentService.commentList(fno, page);
        model.addAttribute("commentList", commentList);

        return "free/freeDetail";
    }

    @GetMapping("edit.do")
    public String freeEditForm(@RequestParam int fno, Model model) throws Exception {
        Free free = freeService.getFree(fno);
        model.addAttribute("detail", free);
        return "free/freeEdit";
    }

    @PostMapping("edit.do")
    public String freeEdit(Free free, RedirectAttributes rttr) throws Exception {
        int result = freeService.updateFree(free);
        if(result > 0) {
            rttr.addFlashAttribute("msg", "게시글을 수정하였습니다.");
        } else {
            rttr.addFlashAttribute("msg", "게시글 수정에 실패했습니다. 잠시 후 다시 시도해주세요.");
        }
        return "redirect:detail.do?fno=" + free.getFno();
    }

    @GetMapping("delete.do")
    public String freeDelete(@RequestParam int fno, RedirectAttributes rttr) throws Exception {
        int result = freeService.deleteFree(fno);
        if (result > 0) {
            rttr.addFlashAttribute("msg", "게시글을 삭제하였습니다.");
        } else {
            rttr.addFlashAttribute("msg", "게시글 삭제에 실패했습니다. 잠시 후 다시 시도해주세요");
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
            String path = request.getRealPath("/upload/free/");
            String ckUploadPath = path + uid + "_" + fileName;
            File folder = new File(path);
            System.out.println("path:"+path);	// 이미지 저장경로 console에 확인
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
            String fileUrl = contextPath + "/free/ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName; // 작성화면

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
        String path = request.getRealPath("/upload/free/");
        String sDirPath = path + uid + "_" + fileName;
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
