package com.asset.reservation.asset.service;

import com.asset.reservation.asset.entity.Asset;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Predicate;

public interface AssetService {
  int getPaginatedAssetsByTypeNameResults(String typeName);

  Page<Asset> getPaginatedAssetsByTypeName(
      String typename, int pageNumber, int pageSize, Predicate<List<Asset>> predicate);

  Asset getAsset(String _id);

  void saveAsset(Asset asset);
}
