package com.travel.smartcity.service;

import com.travel.smartcity.dao.PackageDao;
import com.travel.smartcity.model.Package;

import java.math.BigDecimal;
import java.util.List;

public class PackageService {
  private PackageDao packageDao = new PackageDao();

  public boolean create(Package pack) {
    return packageDao.create(pack);
  }

  public List<Package> listByPlaceId(int placeId) {
    return packageDao.listByPlaceId(placeId);
  }

  public boolean update(Package pack) {
    return packageDao.update(pack);
  }

  public boolean delete(int id) {
    return packageDao.delete(id);
  }

  public BigDecimal getPackagePrice(int pkgId) {
    return packageDao.getPriceById(pkgId);
  }
}
