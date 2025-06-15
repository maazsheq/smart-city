package com.travel.smartcity.service;

import com.travel.smartcity.dao.PayeeDAO;
import com.travel.smartcity.model.PayeeCardDetails;

public class PayeeService {
  private PayeeDAO payeeDAO = new PayeeDAO();

  public boolean addPayee(PayeeCardDetails payeeCardDetails) {
    return payeeDAO.create(payeeCardDetails);
  }

  public PayeeCardDetails getPayeeById(int id) {
    return payeeDAO.findById(id);
  }

//  public List<Payee> listPayees() {
//    return payeeDAO.findAll();
//  }

  public boolean createOrUpdateDefaultPayee(PayeeCardDetails payeeCardDetails) {
    return payeeDAO.upsertDefaultPayee(payeeCardDetails);
  }

  public PayeeCardDetails getDefaultPayee(int userId) {
    return new PayeeDAO().findDefaultByUser(userId);
  }

}