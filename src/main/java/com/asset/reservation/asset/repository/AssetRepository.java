package com.asset.reservation.asset.repository;

import com.asset.reservation.asset.entity.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AssetRepository extends PagingAndSortingRepository<Asset, Long> {
  Asset findAssetBy_id(String _id);

  Page<Asset> findAllByAssetTypeTypeName(String typeName, Pageable pageable);
}
