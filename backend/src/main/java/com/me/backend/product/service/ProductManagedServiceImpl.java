package com.me.backend.product.service;

import com.me.backend.member.dto.OrderDTO;
import com.me.backend.product.dto.InquiryDTO;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.dto.ReviewDTO;
import com.me.backend.product.dto.optionDTO;
import com.me.backend.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductManagedServiceImpl implements ProductManagedService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> bestGoods() {
        return productMapper.bestGoods();
    }

    @Override
    public int searchGoodsCount(Map<String, String> map) {
        return productMapper.searchGoodsCount(map);
    }

    @Override
    public List<ProductDTO> searchGoods(Map<String, Object> map) {
        return productMapper.searchGoods(map);
    }

    @Override
    public int productCount(Map<String, String> map) {
        return productMapper.productCount(map);
    }

    @Override
    public List<ProductDTO> findProduct(Map<String, Object> map) {
        return productMapper.findProduct(map);
    }

    @Override
    public List<ProductDTO> categoryList() {
        return productMapper.categoryList();
    }

    @Override
    public int inputProduct(ProductDTO product) {
        return productMapper.inputProduct(product);
    }

    @Override
    public int inputOptions(List<ProductDTO> options) {
        return productMapper.inputOptions(options);
    }

    @Override
    public int modifyProductsCount(Map<String, String> map) {
        return productMapper.modifyProductsCount(map);
    }

    @Override
    public List<ProductDTO> findModifyProducts(Map<String, Object> map) {
        return productMapper.findModifyProducts(map);
    }

    @Override
    public List<ProductDTO> findAllModifyProducts(Map<String, String> map) {
        return productMapper.findAllModifyProducts(map);
    }

    @Override
    public int modifyProduct(ProductDTO product) {
        return productMapper.modifyProduct(product);
    }

    @Override
    public int modifyOptions(List<ProductDTO> options) {
        return productMapper.modifyOptions(options);
    }

    @Override
    public int inputModifyProducts(List<ProductDTO> products) {
        return productMapper.inputModifyProducts(products);
    }

    @Override
    public int modifyProductImg(Map<String, String> map) {
        return productMapper.modifyProductImg(map);
    }

    @Override
    public ProductDTO modifyImg(String id) {
        return productMapper.modifyImg(id);
    }

    @Override
    public int goodsCount(Map<String, String> map) {
        return productMapper.goodsCount(map);
    }

    @Override
    public List<ProductDTO> goods(Map<String, Object> map) {
        return productMapper.goods(map);
    }

    @Override
    public List<ProductDTO> findGoods(String id) {
        return productMapper.findGoods(id);
    }

    @Override
    public List<optionDTO> goodsPriceRange(String id) {
        return productMapper.goodsPriceRange(id);
    }

    @Override
    public int orderCount(String id) {
        return productMapper.orderCount(id);
    }

    @Override
    public int orderSearchCount(Map<String, Object> map) {
        return productMapper.orderSearchCount(map);
    }

    @Override
    public List<OrderDTO> order(Map<String, Object> map) {
        return productMapper.order(map);
    }

    @Override
    public int modifyOrder(OrderDTO order) {
        return productMapper.modifyOrder(order);
    }

    @Override
    public int modifyGoodsStock(OrderDTO order) {
        return productMapper.modifyGoodsStock(order);
    }

    @Override
    public int refundCount(String id) {
        return productMapper.refundCount(id);
    }

    @Override
    public int refundSearchCount(Map<String, Object> map) {
        return productMapper.refundSearchCount(map);
    }

    @Override
    public List<OrderDTO> refund(Map<String, Object> map) {
        return productMapper.refund(map);
    }

    @Override
    public int modifyRefund(int id) {
        return productMapper.modifyRefund(id);
    }

    @Override
    public int checkOrder(Map<String, String> map) {
        return productMapper.checkOrder(map);
    }

    @Override
    public String checkReview(Map<String, String> map) {
        return productMapper.checkReview(map);
    }

    @Override
    public int inputReview(ReviewDTO review) {
        return productMapper.inputReview(review);
    }

    @Override
    public List<ReviewDTO> review(String id) {
        return productMapper.review(id);
    }

    @Override
    public ReviewDTO findReview(ReviewDTO review) {
        return productMapper.findReview(review);
    }

    @Override
    public int modifyReview(ReviewDTO review) {
        return productMapper.modifyReview(review);
    }

    @Override
    public int sellerReviewCount(Map<String, Object> map) {
        return productMapper.sellerReviewCount(map);
    }

    @Override
    public List<ReviewDTO> sellerReview(Map<String, Object> map) {
        return productMapper.sellerReview(map);
    }

    @Override
    public int deleteReview(ReviewDTO review) {
        return productMapper.deleteReview(review);
    }

    @Override
    public int goodsReviewCount(Map<String, Object> map) {
        return productMapper.goodsReviewCount(map);
    }

    @Override
    public List<ReviewDTO> goodsReview(Map<String, Object> map) {
        return productMapper.goodsReview(map);
    }

    @Override
    public int inputInquiry(InquiryDTO inquiry) {
        return productMapper.inputInquiry(inquiry);
    }

    @Override
    public int goodsInquiryCount(Map<String, Object> map) {
        return productMapper.goodsInquiryCount(map);
    }

    @Override
    public List<InquiryDTO> goodsInquiry(Map<String, Object> map) {
        return productMapper.goodsInquiry(map);
    }

    @Override
    public int sellerInquiryCount(Map<String, Object> map) {
        return productMapper.sellerInquiryCount(map);
    }

    @Override
    public List<InquiryDTO> sellerInquiry(Map<String, Object> map) {
        return productMapper.sellerInquiry(map);
    }

    @Override
    public int modifyInquiry(InquiryDTO inquiry) {
        return productMapper.modifyInquiry(inquiry);
    }

    @Override
    public int inquiryCount(String id) {
        return productMapper.inquiryCount(id);
    }

}
