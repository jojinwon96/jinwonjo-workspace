package com.me.backend.product.service;

import com.me.backend.member.dto.OrderDTO;
import com.me.backend.product.dto.InquiryDTO;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.dto.ReviewDTO;
import com.me.backend.product.dto.optionDTO;

import java.util.List;
import java.util.Map;

public interface ProductManagedService {

    List<ProductDTO> bestGoods();

    int searchGoodsCount (Map<String, String> map);

    List<ProductDTO> searchGoods (Map<String, Object> map);

    int productCount(Map<String, String> map);

    List<ProductDTO> findProduct(Map<String, Object> map);

    List<ProductDTO> categoryList();

    int inputProduct(ProductDTO product);

    int inputOptions(List<ProductDTO> options);

    int modifyProductsCount (Map<String, String> map);

    List<ProductDTO> findModifyProducts(Map<String, Object> map);

    List<ProductDTO> findAllModifyProducts (Map<String, String> map);

    int modifyProduct (ProductDTO product);

    int modifyOptions (List<ProductDTO> options);

    int inputModifyProducts (List<ProductDTO> products);

    int modifyProductImg (Map<String, String> map);

    ProductDTO modifyImg (String id);

    int goodsCount (Map<String, String> map);

    List<ProductDTO> goods (Map<String, Object> map);

    List<ProductDTO> findGoods(String id);

    List<optionDTO> goodsPriceRange(String id);

    int orderCount (String id);

    int orderSearchCount (Map<String, Object> map);

    List<OrderDTO> order (Map<String, Object> map);

    int modifyOrder (OrderDTO order);

    int modifyGoodsStock (OrderDTO order);

    int refundCount (String id);

    int refundSearchCount (Map<String, Object> map);

    List<OrderDTO> refund (Map<String, Object> map);

    int modifyRefund (int id);

    int checkOrder (Map<String, String> map);

    String checkReview (Map<String, String> map);

    int inputReview (ReviewDTO review);

    List<ReviewDTO> review (String id);

    ReviewDTO findReview (ReviewDTO review);

    int modifyReview (ReviewDTO review);

    int sellerReviewCount (Map<String, Object> map);

    List<ReviewDTO> sellerReview (Map<String, Object> map);

    int deleteReview (ReviewDTO review);

    int goodsReviewCount (Map<String, Object> map);

    List<ReviewDTO> goodsReview (Map<String, Object> map);

    int inputInquiry (InquiryDTO inquiry);

    int goodsInquiryCount (Map<String, Object> map);

    List<InquiryDTO> goodsInquiry (Map<String, Object> map);

    int sellerInquiryCount (Map<String, Object> map);

    List<InquiryDTO> sellerInquiry (Map<String, Object> map);

    int modifyInquiry (InquiryDTO inquiry);

    int inquiryCount (String id);
}
