package com.miner.sell.service.impl;

import com.miner.sell.dataobject.ProductInfo;
import com.miner.sell.dto.CartDTO;
import com.miner.sell.enums.ProductStatusEnum;
import com.miner.sell.enums.ResultEnum;
import com.miner.sell.exception.SellException;
import com.miner.sell.repository.ProductInfoRepository;
import com.miner.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 洪峰
 * @create 2018-01-19 20:57
 **/

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService{

    @Autowired
    ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 减少库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void deductStork(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    /**
     * 增加库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void insertStork(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    public ProductInfo offSet(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo == null){
            log.error("该商品不存在={}",productId);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if(ProductStatusEnum.UP.getCode().equals(productInfo.getProductStatus())){
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        }else {
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        }
        productInfo.setUpdateTime(new Date());
        return repository.save(productInfo);
    }
}
