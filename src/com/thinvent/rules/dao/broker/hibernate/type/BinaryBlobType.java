package com.thinvent.rules.dao.broker.hibernate.type;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class BinaryBlobType implements UserType {

	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;

		byte[] bytes = (byte[]) value;
		byte[] result = new byte[bytes.length];
		System.arraycopy(bytes, 0, result, 0, bytes.length);

		return result;
	}

	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y) || (x != null && y != null && java.util.Arrays.equals((byte[]) x, (byte[]) y));
	}

	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isMutable() {
		return true;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		Blob blob = rs.getBlob(names[0]);
		if (blob != null) {
			return blob.getBytes(1, (int) blob.length());
		} else
			return null;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value != null) {
			st.setBlob(index, Hibernate.createBlob((byte[]) value));
		} else {
			st.setNull(index, java.sql.Types.BLOB);
		}
	}

	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Class returnedClass() {
		return byte[].class;
	}

	public int[] sqlTypes() {
		return new int[] { Types.BLOB };
	}

}
