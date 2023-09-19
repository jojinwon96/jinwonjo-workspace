package com.me.backend.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.backend.common.Pagination;
import com.me.backend.common.pasingProduct;
import com.me.backend.member.dto.MemberDTO;
import com.me.backend.member.dto.OrderDTO;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.dto.goodsView;
import com.me.backend.product.dto.optionDTO;
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

    @PostMapping("/api/goods")
    public ResponseEntity goods(@RequestBody Map<String, String> params){

        int count = productService.goodsCount(params.get("category_id"));

        // 페이징
        Pagination pagination = new Pagination();
        pagination.pageInfo(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("range")), count);

        // 페이징정보
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pagination);
        map.put("category_id", params.get("category_id"));
        map.put("sortTarget", params.get("sortTarget"));

        // 카테고리 상품
        List<ProductDTO> goods = productService.goods(map);

        // 게시물 + 페이징 정보 + 옵션 리스트
        pasingProduct goodsInfo = new pasingProduct();
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
        pasingProduct product = new pasingProduct();
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
        pasingProduct product = new pasingProduct();
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

        System.out.println("isAdd" + isAdd);

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
            if (!isAdd.equals("")){
                productService.inputModifyProducts(productList);
            }

            if (result1 == 0){
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

                if (result2 == 0){
                    return new ResponseEntity(result2, HttpStatus.OK);
                }

            } else {
                result2 = result1;
            }
        }

        return new ResponseEntity(result2, HttpStatus.OK);

    }

    @GetMapping("/api/seller/order-count")
    public ResponseEntity orderCount(HttpSession session){

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        int count = productService.orderCount(seller.getSeller_id());

        System.out.println(count);

        return new ResponseEntity(count, HttpStatus.OK);
    }

    @GetMapping("/api/seller/order")
    public ResponseEntity order(HttpSession session){

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        List<OrderDTO> orderList = productService.order(seller.getSeller_id());

        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @PostMapping("/api/goods/view")
    public ResponseEntity findGoods(@RequestBody String id){

        String product_id =id.substring(0, id.length() - 1);

        List<ProductDTO> goods = productService.findGoods(product_id);

        List<optionDTO> priceRange = productService.goodsPriceRange(product_id);

        goodsView data = new goodsView();
        data.setGoods(goods);
        data.setOptions(priceRange);

        return new ResponseEntity(data, HttpStatus.OK);
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
