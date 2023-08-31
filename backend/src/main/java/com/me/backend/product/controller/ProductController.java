package com.me.backend.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.backend.common.Pagination;
import com.me.backend.common.pasingProduct;
import com.me.backend.member.dto.MemberDTO;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.service.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/api/seller/category")
    public ResponseEntity getOption(){

        List<ProductDTO> categoryList = productService.categoryList();

        return new ResponseEntity(categoryList, HttpStatus.OK);
    }

    @PostMapping("/api/seller/pasing/product")
    public ResponseEntity findProducts(@RequestBody Map<String, String> params, HttpSession session){

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
    public ResponseEntity findModifyProduct(@RequestBody Map<String, String> params, HttpSession session){

        MemberDTO seller = (MemberDTO) session.getAttribute("loginMember");

        params.put("id", seller.getSeller_id());

        // 전체 게시글 개수
        int productCount = productService.modifyProductCount(params);

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
        List<ProductDTO> modifyProduct = productService.findModifyProduct(map);

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
                                      @RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {

        int status = 0;

        if (files == null || files.isEmpty()){
            return new ResponseEntity(status, HttpStatus.OK);
        }

        // 파일 저장
        String[] fileNameList = saveFile(files);

        // JSON Array 맵핑
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO[] products = objectMapper.readValue(data, new TypeReference<ProductDTO[]>() {});
        List<ProductDTO> productList = new ArrayList(Arrays.asList(products));

        productList.get(0).setImg1(fileNameList[0]);
        productList.get(0).setImg2(fileNameList[1]);
        productList.get(0).setImg3(fileNameList[2]);
        productList.get(0).setImg4(fileNameList[3]);
        productList.get(0).setImg5(fileNameList[4]);

        for (ProductDTO product : productList){
            System.out.println(product);
        }
        int result = productService.inputProduct(productList.get(0));

        if (result > 0){
            status = productService.inputOptions(productList);
        } else {
            status = 2;
        }


        return new ResponseEntity(status, HttpStatus.OK);
    }

    private String[] saveFile(List<MultipartFile> files) throws IOException {
        // 파일 저장 경로
        String uploadFiePath = System.getProperty("user.dir") + "\\frontend\\src\\assets\\product\\uploadfile\\";

        // 저장될 파일 내임
        String savedFile = "";

        // 여러 개의 파일 이름 저장할 리스트 생성
        String[] fileNameList = {"", "", "", "", ""};

        for (int i = 0; i < files.size(); i++){

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
