package com.asset.reservation.asset.service.impl;

import com.asset.reservation.asset.entity.Asset;
import com.asset.reservation.asset.exception.BusinessException;
import com.asset.reservation.asset.repository.AssetRepository;
import com.asset.reservation.asset.service.AssetService;
import com.asset.reservation.asset.util.enums.BusinessExceptionLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class AssetServiceImpl implements AssetService {

  public static final int DEFAULT_PAGE_NUMBER = 1;
  public static final int DEFAULT_PAGE_SIZE = 10;
  private final AssetRepository assetRepository;

  @Autowired
  public AssetServiceImpl(AssetRepository assetRepository) {
    this.assetRepository = assetRepository;
  }

  @Override
  public int getPaginatedAssetsByTypeNameResults(String typeName) {
    Pageable pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
    Page<Asset> results = assetRepository.findAllByAssetTypeTypeName(typeName, pageRequest);
    return results.getTotalPages();
  }

  @Override
  public Page<Asset> getPaginatedAssetsByTypeName(
      String typeName, int pageNumber, int pageSize, Predicate<List<Asset>> predicate) {
    Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
    Page<Asset> allByAssetTypeTypeName =
        assetRepository.findAllByAssetTypeTypeName(typeName, pageRequest);
    if (predicate.test(allByAssetTypeTypeName.getContent())) {
      throw new BusinessException(BusinessExceptionLabels.CATALOG_EMPTY);
    }
    return allByAssetTypeTypeName;
  }

  @Override
  public Asset getAsset(String _id) throws BusinessException {
    Asset asset = assetRepository.findAssetBy_id(_id);
    if (asset == null) {
      throw new BusinessException(BusinessExceptionLabels.ASSET_NOT_FOUND);
    }
    return asset;
  }

  @Override
  public void saveAsset(Asset asset) {
    assetRepository.save(asset);
  }
}
