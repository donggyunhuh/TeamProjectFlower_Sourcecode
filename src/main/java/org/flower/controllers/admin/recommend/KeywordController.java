package org.flower.controllers.admin.recommend;

import org.flower.entities.Keywords;
import org.flower.models.recommend.keyword.KeywordEditService;
import org.flower.models.recommend.keyword.KeywordInfo;
import org.flower.models.recommend.keyword.KeywordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 관리자 페이지 키워드 추가, 수정, 삭제 관련 컨트롤러 */
@Controller
@RequestMapping("/admin/recommend")
public class KeywordController {

    @Autowired
    private KeywordInfoService keywordInfoService;

    @Autowired
    private KeywordEditService keywordEditService;

    //키워드 관련 메소드들
    @GetMapping("/keyword")
    public String keyword(Model model){
        List<Keywords> keywordsList = keywordInfoService.getAllKeywords();
        model.addAttribute("keywordsList", keywordsList);
        return "admin/recommend/keyword";
    }

    /**
     * 키워드 추가 - POST
     *
     * */
    @PostMapping("/addKeyword")
    public String addKeyword(@RequestParam String keywordNm, RedirectAttributes redirectAttributes){
        try {
            keywordEditService.addKeyword(keywordNm);
            redirectAttributes.addFlashAttribute("successMessage", "키워드가 성공적으로 추가되었습니다.");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "키워드 추가 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/recommend/keyword";
    }

    /*
     * 키워드 수정 - POST
     * */
    @PostMapping("/editKeyword")
    public ResponseEntity<Map<String, Object>> editKeyword(@RequestBody KeywordInfo keywordInfo){
        Map<String, Object> response = new HashMap<>();
        try {
            keywordEditService.editKeyword(keywordInfo);
            response.put("success", true);
            response.put("message", "키워드가 성공적으로 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "키워드 수정 중 오류가 발생했습니다.");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 키워드 삭제 - Delete
     * */
    @DeleteMapping("/deleteKeywords")
    public ResponseEntity<Map<String, Object>> deleteKeywords(@RequestBody Map<String, List<Long>> payload){
        Map<String, Object> response = new HashMap<>();
        try {
            List<Long> keywordNos = payload.get("keywordNos");
            keywordEditService.deleteKeywords(keywordNos);
            response.put("success", true);
            response.put("message", "키워드가 성공적으로 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("success", false);
            response.put("message", "키워드 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
