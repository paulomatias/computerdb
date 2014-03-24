package com.excilys.transfert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class MapperDTO {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/*
	 * transform a computer dto object to a computer
	 */
	public Computer toComputer(ComputerDTO computerDTO) throws ParseException {
		Long id = computerDTO.getId();
		String name = computerDTO.getName();
		String introduced = computerDTO.getIntroduced();
		String discontinued = computerDTO.getDiscontinued();
		Long companyId = computerDTO.getCompany();
		Date introducedDate = null;
		Date discontinuedDate = null;
		if (!introduced.equals("")) {
			introducedDate = FORMAT.parse(introduced);
		}
		if (!introduced.equals("")) {
			discontinuedDate = FORMAT.parse(discontinued);
		}

		Company company = Company.builder().id(companyId).build();
		Computer computer = Computer.builder().id(id).name(name)
				.introduced(introducedDate).discontinued(discontinuedDate)
				.company(company).build();
		return computer;
	}

	/*
	 * transform a computer to a computer dto object
	 */
	public ComputerDTO toDTO(Computer computer) {
		String introduced;
		String discontinued;
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		} else
			introduced = null;
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		} else
			discontinued = null;
		ComputerDTO computerDTO = ComputerDTO.builder().id(computer.getId())
				.name(computer.getName()).introduced(introduced)
				.discontinued(discontinued)
				.company(computer.getCompany().getId()).build();
		return computerDTO;
	}

}
