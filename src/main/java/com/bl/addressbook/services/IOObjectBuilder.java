package com.bl.addressbook.services;

import java.nio.file.Path;

import com.bl.addressbook.ioservices.AddressBookCSVFileOperations;
import com.bl.addressbook.ioservices.AddressBookJDBCServices;
import com.bl.addressbook.ioservices.AddressBookJsonFileService;
import com.bl.addressbook.ioservices.IOServices;

public class IOObjectBuilder {

	private IOObjectBuilder() {
	}

	public static IOServices getIOObject(IOType ioType, Path filePath) {
		if (ioType == IOType.CSV_IO)
			return new AddressBookCSVFileOperations(filePath);
		if (ioType == IOType.JSON_IO)
			return new AddressBookJsonFileService(filePath);
		if (ioType == IOType.DB_IO)
			return AddressBookJDBCServices.getInstance();
		return null;
	}
}
