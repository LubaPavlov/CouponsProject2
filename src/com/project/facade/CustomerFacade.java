package com.project.facade;

public class CustomerFacade implements CouponClientFacade {
 
  private CustomerDAO customerDAO;
  
  public CustomerFacade() {
  }
  
  	public CustomerFacade(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
  
  @Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
