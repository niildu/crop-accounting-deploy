package com.cropaccounting.saml;

import java.util.List;

import org.opensaml.saml2.core.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Noor
 * @email niildu@gmail.com
 * @since Jul 15, 2018
 *
 */
@Component
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);

	@Value("#{'${saml.admin.users:01753420663,01712927449}'.split(',')}")
	List<String> adminMobiles;

	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
		String ssoID = credential.getAuthenticationAssertion().getSubject().getSubjectConfirmations().get(0).getSubjectConfirmationData().getInResponseTo();
		LOG.info("######################### SAML attributes ###############################");
		LOG.info("ssoid = {}", ssoID);
		LOG.info("========== All attributes =============");
		for(Attribute at : credential.getAttributes()) {
			if (!"apitoken".equalsIgnoreCase(at.getName()))
				LOG.info("{} = {}", at.getName(), credential.getAttributeAsString(at.getName()));
		}
		LOG.info("######################### SAML attributes ###############################");

		String mobile = credential.getAttributeAsString("username");
		String role = adminMobiles.contains(mobile) ? "ADMIN" : credential.getAttributeAsString("groups").toUpperCase();
		// In a real scenario, this implementation has to locate user in a arbitrary
		// dataStore based on information present in the SAMLCredential and
		// returns such a date in a form of application specific UserDetails object.
		return User.withUsername(mobile)
					.roles(role)
					.password("<makehappy>").build();
	}
}
