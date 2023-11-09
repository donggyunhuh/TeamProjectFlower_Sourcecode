package org.flower.controllers.user.mypage;


import org.flower.commons.constants.OrderState;
import org.flower.commons.constants.UserRole;
import org.flower.entities.Order;
import org.flower.models.order.order.OrderInfoService;
import org.flower.models.user.UserInfo;
import org.flower.repositories.OrderRepository;
import org.flower.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;



@Controller
@RequestMapping("/user/mypage")
public class MypageHomeController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    // 화면 닉네임 동적으로 받아오는 메소드
    @GetMapping
    public String showMyNickname(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserInfo) {
            UserInfo currentUser = (UserInfo) authentication.getPrincipal();
            Long userNo = currentUser.getUserNo();



            // 로그인한 사용자의 ACCEPTING 상태인 주문 수 계산
            long acceptingOrderCount = orderRepository.findByUser_UserNo(userNo).stream()
                    .filter(order -> order.getOrderStatus() == OrderState.ACCEPTING)
                    .count();

            // 주문 상태 개수를 모델에 추가
            model.addAttribute("acceptingOrderCount", acceptingOrderCount);

            //로그인한 사용자의 닉네임을 모델에 추가
            String userNickNm = currentUser.getUserNickNm();
            model.addAttribute("userNickNm", userNickNm);

            // userNo를 사용하여 추가적인 회원 정보를 조회할 수 있습니다.
            // 예: userProfile, userPosts 등
            // 아래는 단순히 userNo만 모델에 추가하는 예입니다.
            model.addAttribute("userNo", userNo);

        } else {
            // 로그인하지 않은 사용자 또는 기타 상황에 대한 처리
            return "redirect:/user/login"; //
        }

        return "/front/mypage/main/home"; // mypage.html 또는 mypage.jsp와 같은 뷰 파일을 렌더링
    }

    // 현재 로그인한 사용자의 userNo를 가져오고 모델에 주문 내역 엔티티 추가, 주문 내역 html 페이지 리턴
    @GetMapping("/main/home/orderlist")
    public String showMyOrder(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserInfo) {
            UserInfo currentUser = (UserInfo) authentication.getPrincipal();
            Long userNo = currentUser.getUserNo();
            //UserRole currentUserRole = currentUser.getRole();


            // 로그인한 사용자의 ACCEPTING 상태인 주문 수 계산
            long acceptingOrderCount = orderRepository.findByUser_UserNo(userNo).stream()
                    .filter(order -> order.getOrderStatus() == OrderState.ACCEPTING)
                    .count();

            // 주문 상태 개수를 모델에 추가
            model.addAttribute("acceptingOrderCount", acceptingOrderCount);


            // 로그인한 사용자의 주문 목록을 가져와서 모델에 추가
            List<Order> userOrders = orderInfoService.getOrdersByUserNo(userNo);
            model.addAttribute("userOrders", userOrders);


            String userNickNm = currentUser.getUserNickNm();
            model.addAttribute("userNickNm", userNickNm);

            // userNo를 사용하여 추가적인 회원 정보를 조회할 수 있습니다.
            // 예: userProfile, userPosts 등
            // 아래는 단순히 userNo만 모델에 추가하는 예입니다.
            model.addAttribute("userNo", userNo);


            if (currentUser.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + UserRole.OWNER.name()))) {
                return "/front/mypage/main/orderList_Owner"; // OWNER를 위한 뷰 또는 리다이렉트 경로
            } else {
                return "/front/mypage/main/home_orderlist"; // 일반 사용자를 위한 뷰 또는 리다이렉트 경로
            }

        } else {
            // 로그인하지 않은 사용자 또는 기타 상황에 대한 처리
            return "redirect:/user/login"; //
        }
        
    }

}
