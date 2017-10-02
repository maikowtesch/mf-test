package com.mftest.core.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.mftest.core.enumerations.OperationStatus;
import com.mftest.core.enumerations.Roles;
import com.mftest.core.exception.PersistenceCoreException;
import com.mftest.core.exception.UserAlreadyExistsCoreException;
import com.mftest.core.exception.UserNotFoundCoreException;
import com.mftest.core.persistence.UserPersistenceInterface;
import com.mftest.core.persistence.entity.Role;
import com.mftest.core.persistence.entity.User;
import com.mftest.core.responsemodel.UserInfo;


public class LoginBusinessTest {
	
	Mockery mockery;
	private UserPersistenceInterface userPersistence;
	
	@Before
	public void init() {
		mockery = new Mockery();
		userPersistence = mockery.mock(UserPersistenceInterface.class);
	}
	
	// Scenarios to test "validateLogin"
	
	@Test
	public void testValidateLogin_ValidLogin() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).findByUserData(with("john.cena"), with("1234"));
			will(returnValue(user));
		}});
		
		LoginBusiness loginBusiness = new LoginBusiness(userPersistence);
		UserInfo userInfo = new UserInfo("", 0, 0, "");
		try {
			userInfo = loginBusiness.validateLogin("john.cena", "1234");
		} catch (UserNotFoundCoreException | PersistenceCoreException e) {
			fail();
		}
		
		assertEquals(userInfo.getUserName(), user.getName());
		assertEquals(userInfo.getUserId(), user.getId());
		assertEquals(userInfo.getRoleId(), user.getRole().getId());
		assertEquals(userInfo.getRoleDescription(), user.getRole().getDescription());
	}
	
	@Test
	public void testValidateLogin_UserNotFound() throws PersistenceCoreException {
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).findByUserData(with("john.cena"), with("1234"));
			will(returnValue(null));
		}});
		
		LoginBusiness loginBusiness = new LoginBusiness(userPersistence);
		try {
			loginBusiness.validateLogin("john.cena", "1234");
			fail();
		} catch (UserNotFoundCoreException e) {
			assertNotNull(e);
		} catch (PersistenceCoreException e) {
			fail();
		}
	}
	
	
	// Scenarios to test "createLogin"
	
	@Test
	public void testCreateLogin_Success() throws PersistenceCoreException {
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).findByUserData(with("john.cena"));
			will(returnValue(null));
			
			oneOf(userPersistence).save(with(any(User.class)));
		}});
		
		OperationStatus status = OperationStatus.UPDATED;
		
		LoginBusiness loginBusiness = new LoginBusiness(userPersistence);
		try {
			status = loginBusiness.createLogin("john.cena", "1234");
		} catch (UserAlreadyExistsCoreException | PersistenceCoreException e) {
			fail();
		}
		
		assertEquals(status, OperationStatus.INSERTED);
	}
	
	@Test
	public void testCreateLogin_UserAlreadyExists() throws PersistenceCoreException {
		User user = new User("john.cena", "1234", new Role(Roles.USER.getCode(), Roles.USER.getDescription()));
		
		mockery.checking(new Expectations() {{
			oneOf(userPersistence).findByUserData(with("john.cena"));
			will(returnValue(user));
		}});
		
		LoginBusiness loginBusiness = new LoginBusiness(userPersistence);
		try {
			loginBusiness.createLogin("john.cena", "1234");
			fail();
		} catch (UserAlreadyExistsCoreException e) {
			assertNotNull(e);
		} catch (PersistenceCoreException e) {
			fail();
		}
	}
	
}
