package com.asset.reservation.asset.util.enums;

import lombok.Getter;

@Getter
public enum DataTableAssetHeaders {
  ASSET_HEADER_ASSET_NAME("_id", "_Id"),
  ASSET_HEADER_ASSET_QTY("assetName", "Asset Name"),
  ASSET_HEADER_ASSET_ID("assetQuantity", "Asset Quantity");

  public String key;
  public String value;

  DataTableAssetHeaders(String key, String value) {
    this.value = value;
    this.key = key;
  }
}
