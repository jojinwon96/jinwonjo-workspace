package com.me.backend.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.backend.common.*;
import com.me.backend.member.dto.MemberDTO;
import com.me.backend.member.dto.OrderDTO;
import com.me.backend.product.dto.*;
import com.me.backend.product.service.ProductManagedServiceImpl;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;


@RestController
public class ProductManagedController {

    // 파일 저장 경로
    private static final String uploadFiePath = System.getProperty("user.dir") + "\\frontend\\src\\assets\\product\\uploadfile\\";

    @Autowired
    private ProductManagedServiceImpl productService;

    @GetMapping("/api/home/best")
    public ResponseEntity bestGoods(){

        List<ProductDTO> bestList = productService.bestGoods();

        return new ResponseEntity(bestList, HttpStatus.OK);
    }

    @PostMapping("/api/home/find-goods")
    public ResponseEntity searchGoods(@RequestBody Map<String, String> params){

        int count = productService.searchGoodsCount(params);

        // 페이징
        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), count);

        // 페이징정보
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pagination);
        map.put("category_id", params.get("category_id"));
        map.put("content", params.get("content"));

        // 카테고리 상품
        List<ProductDTO> searchGoods = productService.searchGoods(map);

        // 게시물 + 페이징 정보 + 옵션 리스트
        PagingProduct goodsInfo = new PagingProduct();
        goodsInfo.setProducts(searchGoods);
        goodsInfo.setPagination(pagination);

        return new ResponseEntity(goodsInfo, HttpStatus.OK);

    }

    @PostMapping("/api/goods")
    public ResponseEntity goods(@RequestBody Map<String, String> params) {

        int count = productService.goodsCount(params);

        // 페이징
        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), count);

        // 페이징정보
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pagination);
        map.put("category_id", params.get("category_id"));
        map.put("sortTarget", params.get("sortTarget"));
        map.put("num1", params.get("num1"));
        map.put("num2", params.get("num2"));

        // 카테고리 상품
        List<ProductDTO> goods = productService.goods(map);

        // 게시물 + 페이징 정보 + 옵션 리스트
        PagingProduct goodsInfo = new PagingProduct();
        goodsInfo.setProducts(goods);
        goodsInfo.setPagination(pagination);

        return new ResponseEntity(goodsInfo, HttpStatus.OK);
    }

    @GetMapping("/api/seller/category")
    public ResponseEntity getOption() {

        List<ProductDTO> categoryList = productService.categoryList();

        return new ResponseEntity(categoryList, HttpStatus.OK);
    }

    @PostMapping("/api/seller/pasing/product")
    public ResponseEntity findProduct(@RequestBody Map<String, String> params, HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        params.put("id", seller.getSeller_id());

        // 전체 게시글 개수
        int productCount = productService.productCount(params);

        // 페이징
        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), productCount);

        // 맵 (판매자id + 페이징정보)
        Map<String, Object> map = new HashMap<>();
        map.put("id", seller.getSeller_id());
        map.put("pageInfo", pagination);
        map.put("category_id", params.get("category_id"));
        map.put("keyword", params.get("keyword"));
        map.put("product_id", params.get("product_id"));

        // 게시물
        List<ProductDTO> findProduct = productService.findProduct(map);

        // 옵션 리스트
        List<ProductDTO> categoryList = productService.categoryList();

        // 게시물 + 페이징 정보 + 옵션 리스트
        PagingProduct product = new PagingProduct();
        product.setProducts(findProduct);
        product.setPagination(pagination);
        product.setOptions(categoryList);

        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PostMapping("/api/seller/pasing/modify-product")
    public ResponseEntity findModifyProducts(@RequestBody Map<String, String> params, HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        params.put("id", seller.getSeller_id());

        // 전체 게시글 개수
        int productCount = productService.modifyProductsCount(params);

        // 페이징
        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), productCount);

        // 맵 (판매자id + 페이징정보)
        Map<String, Object> map = new HashMap<>();
        map.put("id", seller.getSeller_id());
        map.put("pageInfo", pagination);
        map.put("category_id", params.get("category_id"));
        map.put("keyword", params.get("keyword"));
        map.put("product_id", params.get("product_id"));

        // 수정할 게시물
        List<ProductDTO> modifyProduct = productService.findModifyProducts(map);

        // 옵션 리스트
        List<ProductDTO> categoryList = productService.categoryList();

        // 게시물 + 페이징 정보 + 옵션 리스트
        PagingProduct product = new PagingProduct();
        product.setProducts(modifyProduct);
        product.setPagination(pagination);
        product.setOptions(categoryList);

        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PostMapping("/api/seller/products")
    public ResponseEntity createProduct(@RequestPart(value = "products") String data,
                                        HttpSession session,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        int status = 0;

        if (files == null || files.isEmpty()) {
            return new ResponseEntity(status, HttpStatus.OK);
        }

        // 파일 저장
        String[] fileNameList = saveFiles(files);

        // JSON Array 맵핑
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO[] products = objectMapper.readValue(data, new TypeReference<ProductDTO[]>() {
        });
        List<ProductDTO> productList = new ArrayList(Arrays.asList(products));

        productList.get(0).setImg1(fileNameList[0]);
        productList.get(0).setImg2(fileNameList[1]);
        productList.get(0).setImg3(fileNameList[2]);
        productList.get(0).setImg4(fileNameList[3]);
        productList.get(0).setImg5(fileNameList[4]);

        for (ProductDTO product : productList) {
            System.out.println(product);
        }
        int result = productService.inputProduct(productList.get(0));

        if (result > 0) {
            status = productService.inputOptions(productList);
        } else {
            status = 2;
        }


        return new ResponseEntity(status, HttpStatus.OK);
    }


    @PostMapping("/api/seller/modify-product")
    private ResponseEntity findModifyProduct(@RequestBody Map<String, String> params, HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");
        params.put("id", seller.getSeller_id());

        List<ProductDTO> products = productService.findAllModifyProducts(params);

        return new ResponseEntity(products, HttpStatus.OK);
    }


    @PostMapping("api/seller/modify-products")
    public ResponseEntity modifyProducts(@RequestPart(value = "products") String data,
                                         @RequestParam("isAdd") String isAdd,
                                         @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        // JSON Array 맵핑
        ObjectMapper objectMapper = new ObjectMapper();

        ProductDTO[] products = objectMapper.readValue(data, new TypeReference<ProductDTO[]>() {
        });

        List<ProductDTO> productList = new ArrayList(Arrays.asList(products));


        int isUpdatedProduct = productService.modifyProduct(productList.get(0));

        int result1 = 0;
        int result2 = 0;

        if (isUpdatedProduct > 0) {
            result1 = productService.modifyOptions(productList);
            if (!isAdd.equals("")) {
                productService.inputModifyProducts(productList);
            }

            if (result1 == 0) {
                return new ResponseEntity(result2, HttpStatus.OK);
            }
        }

        if (result1 > 0) {
            if (file != null) {
                ProductDTO originProduct = productService.modifyImg(productList.get(0).getProduct_id());

                String uploadFile = uploadFiePath + URLDecoder.decode(originProduct.getImg1());

                File deleteFile = new File(uploadFile);

                if (deleteFile.exists()) {
                    deleteFile.delete();
                }

                String saveName = saveFile(file);

                Map<String, String> map = new HashMap<>();
                map.put("id", productList.get(0).getProduct_id());
                map.put("img1", saveName);

                result2 = productService.modifyProductImg(map);

                if (result2 == 0) {
                    return new ResponseEntity(result2, HttpStatus.OK);
                }

            } else {
                result2 = result1;
            }
        }

        return new ResponseEntity(result2, HttpStatus.OK);

    }

    @GetMapping("/api/seller/order-count")
    public ResponseEntity orderCount(HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        int orderCount = productService.orderCount(seller.getSeller_id());
        int refundCount = productService.refundCount(seller.getSeller_id());
        int inquiryCount = productService.inquiryCount(seller.getSeller_id());

        Map<String, Integer> map = new HashMap<>();
        map.put("orderCount", orderCount);
        map.put("refundCount", refundCount);
        map.put("inquiryCount", inquiryCount);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("/api/seller/order")
    public ResponseEntity order(@RequestBody Map<String, String> params, HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        Map<String, Object> map = new HashMap<>();
        map.put("id", seller.getSeller_id());
        map.put("status", params.get("status"));
        map.put("target", params.get("target"));
        map.put("content", params.get("content"));

        int orderCount = productService.orderSearchCount(map);

        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), orderCount);

        // 페이징 정보
        map.put("pageInfo", pagination);

        List<OrderDTO> orderList = productService.order(map);

        PagingOrder pagingOrder = new PagingOrder();
        pagingOrder.setOrder(orderList);
        pagingOrder.setPagination(pagination);

        return new ResponseEntity(pagingOrder, HttpStatus.OK);
    }

    @PostMapping("/api/seller/modify-order")
    public ResponseEntity modifyOrder(@RequestBody OrderDTO order) {

        int result1 = 0;
        int result2 = 0;

        result1 = productService.modifyOrder(order);

        if (result1 > 0){
            result2 = productService.modifyGoodsStock(order);
        }

        return new ResponseEntity(result2, HttpStatus.OK);
    }

    @PostMapping("/api/seller/refund")
    public ResponseEntity refund(@RequestBody Map<String, String> params, HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        Map<String, Object> map = new HashMap<>();
        map.put("id", seller.getSeller_id());
        map.put("refundStatus", params.get("refundStatus"));
        map.put("target", params.get("target"));
        map.put("content", params.get("content"));

        int refundSearchCount = productService.refundSearchCount(map);

        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), refundSearchCount);

        // 페이징 정보
        map.put("pageInfo", pagination);

        List<OrderDTO> refundList = productService.refund(map);

        PagingOrder pagingOrder = new PagingOrder();
        pagingOrder.setOrder(refundList);
        pagingOrder.setPagination(pagination);

        return new ResponseEntity(pagingOrder, HttpStatus.OK);
    }

    @PostMapping("/api/seller/modify-refund")
    public ResponseEntity modifyRefund(@RequestBody OrderDTO order) {

        int result = productService.modifyRefund(order.getOrder_detail_id());

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/goods/view")
    public ResponseEntity findGoods(@RequestBody String id) {
        String product_id = id.substring(0, id.length() - 1);

        List<ProductDTO> goods = productService.findGoods(product_id);
        List<optionDTO> priceRange = productService.goodsPriceRange(product_id);

        goodsView data = new goodsView();
        data.setGoods(goods);
        data.setOptions(priceRange);

        return new ResponseEntity(data, HttpStatus.OK);
    }

    @PostMapping("/api/order/check")
    public ResponseEntity orderCheck(@RequestBody Map<String, String> map, HttpSession session) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        map.put("cust_id", member.getCust_id());

        int result = productService.checkOrder(map);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/goods/check-review")
    public ResponseEntity checkReview(@RequestBody Map<String, String> map, HttpSession session) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        String result = "";

        if (member != null){
            map.put("cust_id", member.getCust_id());

            result = productService.checkReview(map);
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/goods/input-review")
    public ResponseEntity inputReview(@RequestPart(value = "file", required = false) MultipartFile file,
                                      @RequestPart(value = "review") String data,
                                      HttpSession session) throws IOException {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        ObjectMapper objectMapper = new ObjectMapper();
        ReviewDTO review = objectMapper.readValue(data, new TypeReference<ReviewDTO>() {
        });

        String fileName = "";

        if (file != null) {
            fileName = saveFile(file);
            review.setImg(fileName);
        }

        review.setCust_id(member.getCust_id());

        int result = productService.inputReview(review);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/goods/find-review")
    public ResponseEntity findReview(@RequestBody ReviewDTO review, HttpSession session) {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        review.setCust_id(member.getCust_id());

        ReviewDTO findReview = productService.findReview(review);

        return new ResponseEntity(findReview, HttpStatus.OK);
    }

    @PostMapping("/api/goods/modify-review")
    public ResponseEntity modifyReview(@RequestPart(value = "file", required = false) MultipartFile file,
                                       @RequestPart(value = "review") String data,
                                       HttpSession session) throws IOException {

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        ObjectMapper objectMapper = new ObjectMapper();
        ReviewDTO review = objectMapper.readValue(data, new TypeReference<ReviewDTO>() {
        });

        review.setCust_id(member.getCust_id());

        String fileName = "";


        if (file != null) {
            String originFileName = productService.findReview(review).getImg();

            if (originFileName != null) {
                String uploadFile = uploadFiePath + URLDecoder.decode(originFileName);

                File deleteFile = new File(uploadFile);

                if (deleteFile.exists()) {
                    deleteFile.delete();
                }
            }

            fileName = saveFile(file);
            review.setImg(fileName);
        }


        int result = productService.modifyReview(review);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/seller/review")
    public ResponseEntity sellerReview(@RequestBody Map<String, String> params, HttpSession session) {

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        Map<String, Object> map = new HashMap<>();
        map.put("id", seller.getSeller_id());
        map.put("status", params.get("status"));
        map.put("rate", params.get("rate"));
        map.put("target", params.get("target"));
        map.put("content", params.get("content"));

        int reviewCount = productService.sellerReviewCount(map);

        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), reviewCount);

        map.put("pageInfo", pagination);

        List<ReviewDTO> reviewList = productService.sellerReview(map);

        PagingReview pagingReview = new PagingReview();
        pagingReview.setReviews(reviewList);
        pagingReview.setPagination(pagination);

        return new ResponseEntity(pagingReview, HttpStatus.OK);
    }

    @PostMapping("/api/seller/delete-review")
    public ResponseEntity deleteReview(@RequestBody ReviewDTO review){

        int result = productService.deleteReview(review);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/goods/reviewInquiry")
    public ResponseEntity reviewInquiry(@RequestBody Map<String, String> params){

        // 리뷰
        Map<String, Object> reviewMap = new HashMap<>();
        reviewMap.put("id", params.get("product_id"));
        reviewMap.put("rate", params.get("rate"));
        reviewMap.put("content", params.get("content"));

        int reviewCount = productService.goodsReviewCount(reviewMap);

        Pagination reviewPagination = new Pagination();
        reviewPagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), reviewCount);

        reviewMap.put("pageInfo", reviewPagination);

        List<ReviewDTO> reviews = productService.goodsReview(reviewMap);


        // 문의
        Map<String, Object> inquiryMap = new HashMap<>();
        inquiryMap.put("id", params.get("product_id"));
        inquiryMap.put("content", params.get("inquiry_content"));

        int inquiryCount = productService.goodsInquiryCount(inquiryMap);

        Pagination inquiryPagination = new Pagination();
        inquiryPagination.pageInfo(Integer.parseInt(params.get("inquiry_page")), Integer.parseInt(params.get("inquiry_range")), inquiryCount);

        inquiryMap.put("pageInfo", inquiryPagination);

        List<InquiryDTO> inquiryList = productService.goodsInquiry(inquiryMap);

        PagingView reviewInquiry = new PagingView();
        reviewInquiry.setReviews(reviews);
        reviewInquiry.setReviewPagination(reviewPagination);
        reviewInquiry.setInquiryList(inquiryList);
        reviewInquiry.setInquiryPagination(inquiryPagination);

        return new ResponseEntity(reviewInquiry, HttpStatus.OK);
    }

    @PostMapping("/api/goods/input-inquiry")
    public ResponseEntity inputInquiry(@RequestBody InquiryDTO inquiry, HttpSession session){

        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");

        inquiry.setCust_id(member.getCust_id());

        int result = productService.inputInquiry(inquiry);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/seller/inquiry")
    public ResponseEntity sellerInquiry(@RequestBody Map<String, String> params, HttpSession session){

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        Map<String, Object> map = new HashMap<>();
        map.put("id", seller.getSeller_id());
        map.put("status", params.get("status"));
        map.put("target", params.get("target"));
        map.put("content", params.get("content"));

        int count = productService.sellerInquiryCount(map);

        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), count);

        map.put("pageInfo", pagination);

        List<InquiryDTO> inquiryList = productService.sellerInquiry(map);

        PagingInquiry pagingInquiry = new PagingInquiry();
        pagingInquiry.setInquiryList(inquiryList);
        pagingInquiry.setPagination(pagination);

        return new ResponseEntity(pagingInquiry, HttpStatus.OK);
    }

    @PostMapping("/api/seller/modify-inquiry")
    public ResponseEntity modifyInquiry(@RequestBody InquiryDTO inquiry){

        int result = productService.modifyInquiry(inquiry);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    private String saveFile(MultipartFile file) throws IOException {

        // 원본 파일 내임
        String originFileName = file.getOriginalFilename();

        // 파일에 이름 중복 되지 않도록 파일 이름 변경
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + "_" + originFileName;

        String savedFile = uploadFiePath + savedFileName;

        File newFile = new File(savedFile);

        file.transferTo(newFile);

        return savedFileName;
    }

    private String[] saveFiles(List<MultipartFile> files) throws IOException {

        // 저장될 파일 내임
        String savedFile = "";

        // 여러 개의 파일 이름 저장할 리스트 생성
        String[] fileNameList = {"", "", "", "", ""};

        for (int i = 0; i < files.size(); i++) {

            // 원본 파일 이름 알아오기
            String originFileName = files.get(i).getOriginalFilename();

            // 파일에 이름 중복 되지 않도록 파일 이름 변경
            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid.toString() + "_" + originFileName;

            savedFile = uploadFiePath + savedFileName;

            fileNameList[i] = savedFileName;


            File newFile = new File(savedFile);

            files.get(i).transferTo(newFile);
        }

        return fileNameList;
    }

}
