package com.chunjae.pro05.ctrl;

import com.chunjae.pro05.biz.DataRoomService;
import com.chunjae.pro05.biz.FileInfoService;
import com.chunjae.pro05.entity.DataRoom;
import com.chunjae.pro05.entity.FileInfo;
import com.chunjae.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/dataroom/*")
public class DataRoomController {

    @Value("d:\\upload\\dataroom")
    private String uploadFolder;

    @Autowired
    private DataRoomService dataRoomService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private HttpSession session;

    @GetMapping("list.do")
    public String getBoardList(HttpServletRequest request, Model model) throws Exception {
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        Page page = new Page();
        page.setKeyword(request.getParameter("keyword"));
        page.setType(request.getParameter("type"));

        // 페이징에 필요한 데이터 만들기
        int total = dataRoomService.getCount(page);
        page.makePage(total);

        List<DataRoom> dataRoomList = dataRoomService.dataRoomList(page);
        model.addAttribute("dataroomList", dataRoomList);           // 자료 목록
        model.addAttribute("curPage", curPage);                     // 현재 페이지
        model.addAttribute("page", page);                           // 페이징 데이터
        return "/dataroom/dataRoomList";
    }

    @RequestMapping(value = "insert.do", method = RequestMethod.GET)
    public String insertForm() throws Exception {
        return "/dataroom/dataRoomInsert";
    }

    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public String write(DataRoom dataRoom, Principal principal, @RequestParam("upfile") MultipartFile[] files, HttpServletRequest request, Model model, RedirectAttributes rttr) throws Exception {
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = uploadFolder + "\\" + today;
        File folder = new File(saveFolder);
        if(!folder.exists()) {          // 폴더가 존재하지 않으면 폴더 생성
            folder.mkdirs();
        }
        List<FileInfo> fileInfoList = new ArrayList<>();        // 첨부파일 정보를 리스트로 생성
        for(MultipartFile file : files) {
            FileInfo fileInfo = new FileInfo();
            String originalFileName = file.getOriginalFilename(); // 첨부파일의 실제 파일명
            if(!originalFileName.isEmpty()) {
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));     // 파일 이름을 랜덤으로 설정
                fileInfo.setSaveFolder(today);
                fileInfo.setOriginFile(originalFileName);
                fileInfo.setSaveFile(saveFileName);
                file.transferTo(new File(folder, saveFileName));    // 파일을 업로드 폴더에 저장
            }
            fileInfoList.add(fileInfo);
        }
        dataRoom.setFileInfoList(fileInfoList);
        dataRoom.setId(principal.getName());

        try {
            dataRoomService.dataRoomInsert(dataRoom);
            rttr.addFlashAttribute("msg", "자료실에 글을 등록하였습니다");
        } catch(Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("msg", "글 작성 중 문제가 발생했습니다");
        }
        return "redirect:/dataroom/list.do";
    }

    @GetMapping("detail.do")
    public String detail(HttpServletRequest request, Model model) throws Exception {
        int articleNo = request.getParameter("articleNo") != null ? Integer.parseInt(request.getParameter("articleNo")) : 0;
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 0;
        DataRoom dataRoom = dataRoomService.dataRoomDetail(articleNo);
        DataRoom prev = dataRoomService.dataRoomRef(articleNo, "prev");
        DataRoom next = dataRoomService.dataRoomRef(articleNo, "next");
        List<FileInfo> fileList = fileInfoService.fileInfoList(articleNo);

        model.addAttribute("detail", dataRoom);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("fileList", fileList);
        model.addAttribute("curPage", curPage);

        return "/dataroom/dataRoomDetail";
    }

    @GetMapping("edit.do")
    public String editForm(HttpServletRequest request, Model model) throws Exception {
        int articleNo = request.getParameter("articleNo") != null ? Integer.parseInt(request.getParameter("articleNo")) : 0;
        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        DataRoom dataRoom = dataRoomService.dataRoomDetail(articleNo);
        dataRoom.setFileInfoList(fileInfoService.fileInfoList(articleNo));

        model.addAttribute("dataRoom", dataRoom);
        model.addAttribute("curPage", curPage);

        return "/dataroom/dataRoomEdit";
    }

    @PostMapping("edit.do")
    public String dataRoomEdit(DataRoom dataRoom, Principal principal, @RequestParam("upfile") MultipartFile[] files, HttpServletRequest request, RedirectAttributes rttr) throws Exception {

        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = uploadFolder + "\\" + today;
        File folder = new File(saveFolder);
        if(!folder.exists()) {                          // 폴더가 존재하지 않으면 생성함
            folder.mkdirs();
        }

        // 파일이 새롭게 업로드되지 않았다면 삭제하지 않도록 처리
        if(files[0].getSize() != 0) {
            List<FileInfo> oldFiles = fileInfoService.fileInfoList(dataRoom.getArticleNo());
            for (FileInfo fileInfo : oldFiles) {
                File oldFile = new File(uploadFolder + "\\" + fileInfo.getSaveFolder() + "\\" + fileInfo.getSaveFile());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }

        List<FileInfo> fileInfos = new ArrayList<>();   //첨부파일 정보를 리스트로 생성
        for (MultipartFile file : files) {
            FileInfo fileInfo = new FileInfo();
            String originalFileName = file.getOriginalFilename(); //첨부파일의 실제 파일명을 저장
            if (!originalFileName.isEmpty()) {
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.')); // 랜덤으로 파일이름 설정
                fileInfo.setArticleNo(dataRoom.getArticleNo());
                fileInfo.setSaveFolder(today);
                fileInfo.setOriginFile(originalFileName);
                fileInfo.setSaveFile(saveFileName);
                file.transferTo(new File(folder, saveFileName));  // 파일을 업로드 폴더에 저장
            }
            fileInfos.add(fileInfo);
        }
        dataRoom.setFileInfoList(fileInfos);
        dataRoom.setId(principal.getName());

        try {
            dataRoomService.dataRoomEdit(dataRoom);
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("msg", "글 작성중 문제가 발생했습니다");
        }

        rttr.addAttribute("articleNo", dataRoom.getArticleNo());
        rttr.addAttribute("page", request.getParameter("page"));
        return "redirect:/dataroom/detail.do";
    }

    @GetMapping("delete.do")
    public String dataRoomDelete(@RequestParam int articleNo, RedirectAttributes rttr) throws Exception {
        dataRoomService.dataRoomDelete(articleNo);

        List<FileInfo> oldFiles = fileInfoService.fileInfoList(articleNo);
        for (FileInfo fileInfo : oldFiles) {
            File oldFile = new File(uploadFolder + "\\" + fileInfo.getSaveFolder() + "\\" + fileInfo.getSaveFile());
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        return "redirect:/dataroom/list.do";
    }

}
