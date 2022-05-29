package me.daniel.service;

import me.daniel.domain.DTO.OrderReadyDTO;
import me.daniel.domain.DTO.UserDTO;
import me.daniel.domain.VO.KakaoPayApprovalVO;
import me.daniel.domain.VO.KakaoPayReadyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 주문 서비스 <br>
 * 카카오 페이 API 활용하여 카카오 서버에 결제 요청
 *
 * <pre>
 *     <b>History</b>
 *     김남영, 1.0, 2022.05.28 최초 작성
 * </pre>
 *
 * @author 김남영
 * @version 1.0
 */
@Service
public class KakaoPayService {

    private static final String HOST = "https://kapi.kakao.com";
    private static final String APPROVAL_URL = "http://localhost:8080/api/order/pay/completed";
    private static final String CANCEL_URL = "http://localhost:8080/api/order/pay/cancel";
    private static final String FAIL_URL = "http://localhost:8080/api/order/pay/fail";
    private static final String KAKAO_PAY_READY = "/v1/payment/ready";
    private static final String TEST_CID = "TC0ONETIME";
    private static final String ADMIN_KEY = "bde0ce7eee2065336f458eacccdb1a7b";
    private static final Integer TAX_FREE_AMOUNT = 1000;

    private static final Logger log = LoggerFactory.getLogger(KakaoPayService.class);

    private KakaoPayReadyVO kakaoPayReadyVO;
    private RestTemplate restTemplate;
    private String order_id;
    private String userId;
    private String itemName;
    private Integer totalAmount;

    public String kakaoPayReady(OrderReadyDTO orderReadyDTO, UserDTO user) {

        /* 서버로 요청할 헤더*/
        HttpHeaders headers = new HttpHeaders();
        setHeaders(headers);

        order_id = user.getUserId() + orderReadyDTO.getItem_name();
        userId = user.getUserId();
        itemName = orderReadyDTO.getItem_name();
        totalAmount = orderReadyDTO.getTotal_Amount();

        /* 서버로 요청할 body */
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        setParams(params);
        params.add("partner_order_id", order_id);
        params.add("partner_user_id", userId);
        params.add("item_name", itemName);
        params.add("quantity", String.valueOf(orderReadyDTO.getQuantity()));
        params.add("total_amount", String.valueOf(totalAmount));
        params.add("tax_free_amount", String.valueOf(TAX_FREE_AMOUNT));

        return getUrl(headers, params);
    }

    public String cartKakaoPayReady(String[] productNoArr, UserDTO user, int totalAmount) {

        /* 서버로 요청할 헤더*/
        HttpHeaders headers = new HttpHeaders();
        setHeaders(headers);

        itemName = productNoArr[0] + " 그 외 " + (productNoArr.length - 1);
        order_id = user.getUserId() + itemName;
        userId = user.getUserId();
        this.totalAmount = totalAmount;

        /* 서버로 요청할 body */
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        setParams(params);
        params.add("partner_order_id", order_id);
        params.add("partner_user_id", userId);
        params.add("item_name", itemName);
        params.add("item_code", String.join("", productNoArr));
        params.add("quantity", String.valueOf(productNoArr.length));
        params.add("total_amount", String.valueOf(totalAmount));
        params.add("tax_free_amount", String.valueOf(TAX_FREE_AMOUNT));

        return getUrl(headers, params);
    }

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, String jwtToken) {

        /* 서버로 요청할 헤더*/
        HttpHeaders headers = new HttpHeaders();
        setHeaders(headers);
        headers.add("AuthorizationToken", "Bearer " + jwtToken);

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", TEST_CID);
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", order_id);
        params.add("partner_user_id", userId);
        params.add("pg_token", pg_token);
        params.add("total_amount", String.valueOf(totalAmount));

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            return restTemplate.postForObject(new URI(HOST + KAKAO_PAY_READY), body, KakaoPayApprovalVO.class);
        } catch (RestClientException e) {
            log.error(e.getMessage());
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    private void setHeaders(HttpHeaders headers) {
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
    }

    private void setParams(MultiValueMap<String, String> params) {
        params.add("cid", TEST_CID);
        params.add("approval_url", APPROVAL_URL);
        params.add("cancel_url", CANCEL_URL);
        params.add("fail_url", FAIL_URL);
    }

    private String getUrl(HttpHeaders headers, MultiValueMap<String, String> params) {
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            /* 서버 요청 후 응답 객체 받기 */
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + KAKAO_PAY_READY),
                    body, KakaoPayReadyVO.class);

            return kakaoPayReadyVO != null ? kakaoPayReadyVO.getNext_redirect_pc_url() : null;
        } catch (RestClientException e) {
            log.error(e.getMessage());
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}