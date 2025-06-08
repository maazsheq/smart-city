package com.travel.smartcity.service;

import com.travel.smartcity.dao.PayeeDAO;
import com.travel.smartcity.model.Payee;

import java.util.List;

public class PayeeService {
  private PayeeDAO payeeDAO = new PayeeDAO();

  public boolean addPayee(Payee payee) {
    return payeeDAO.create(payee);
  }

  public Payee getPayeeById(int id) {
    return payeeDAO.findById(id);
  }

  public List<Payee> listPayees() {
    return payeeDAO.findAll();
  }
}