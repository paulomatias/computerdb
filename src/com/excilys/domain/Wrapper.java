package com.excilys.domain;

import java.util.List;

public class Wrapper {
	public static final Integer RECORDS_PER_PAGE = 25;
	private Integer recordsPerPage;
	private Integer nbrOfPages;
	private Integer currentPage;
	private String message;
	private String searchCompany;
	private String searchComputer;
	private String orderBy;
	private Long nbrComputers;
	private Computer computer;
	private List<Company> listCompanies;
	private List<Computer> listComputers;

	public static class Builder {
		Wrapper computerWrapper;

		private Builder() {
			computerWrapper = new Wrapper();
		}

		public Builder recordsPerPage(Integer recordsPerPage) {
			this.computerWrapper.recordsPerPage = recordsPerPage;
			return this;
		}

		public Builder nbrOfPages(Integer nbrOfPages) {
			this.computerWrapper.nbrOfPages = nbrOfPages;
			return this;
		}

		public Builder currentPage(Integer currentPage) {
			this.computerWrapper.currentPage = currentPage;
			return this;
		}

		public Builder message(String message) {
			this.computerWrapper.message = message;
			return this;
		}

		public Builder searchCompany(String searchCompany) {
			this.computerWrapper.searchCompany = searchCompany;
			return this;
		}

		public Builder searchComputer(String searchComputer) {
			this.computerWrapper.searchComputer = searchComputer;
			return this;
		}

		public Builder orderBy(String orderBy) {
			this.computerWrapper.orderBy = orderBy;
			return this;
		}

		public Builder nbrComputers(Long nbrComputers) {
			this.computerWrapper.nbrComputers = nbrComputers;
			return this;
		}

		public Builder computer(Computer computer) {
			this.computerWrapper.computer = computer;
			return this;
		}

		public Builder listCompanies(List<Company> listCompanies) {
			this.computerWrapper.listCompanies = listCompanies;
			return this;
		}

		public Builder listComputers(List<Computer> listComputers) {
			this.computerWrapper.listComputers = listComputers;
			return this;
		}

		public Wrapper build() {
			return this.computerWrapper;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public Integer getNbrOfPages() {
		return nbrOfPages;
	}

	public void setNbrOfPages(Integer nbrOfPages) {
		this.nbrOfPages = nbrOfPages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSearchCompany() {
		return searchCompany;
	}

	public void setSearchCompany(String searchCompany) {
		this.searchCompany = searchCompany;
	}

	public String getSearchComputer() {
		return searchComputer;
	}

	public void setSearchComputer(String searchComputer) {
		this.searchComputer = searchComputer;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Long getNbrComputers() {
		return nbrComputers;
	}

	public void setNbrComputers(Long nbrComputers) {
		this.nbrComputers = nbrComputers;
	}

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public List<Company> getListCompanies() {
		return listCompanies;
	}

	public void setListCompanies(List<Company> listCompanies) {
		this.listCompanies = listCompanies;
	}

	public List<Computer> getListComputers() {
		return listComputers;
	}

	public void setListComputers(List<Computer> listComputers) {
		this.listComputers = listComputers;
	}

	@Override
	public String toString() {
		return "ComputerWrapper [nbrOfPages=" + nbrOfPages + ", currentPage="
				+ currentPage + ", message=" + message + ", searchCompany="
				+ searchCompany + ", searchComputer=" + searchComputer
				+ ", nbrComputers=" + nbrComputers + ", listCompanies="
				+ listCompanies + ", listComputers=" + listComputers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentPage == null) ? 0 : currentPage.hashCode());
		result = prime * result
				+ ((listCompanies == null) ? 0 : listCompanies.hashCode());
		result = prime * result
				+ ((listComputers == null) ? 0 : listComputers.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((nbrComputers == null) ? 0 : nbrComputers.hashCode());
		result = prime * result
				+ ((nbrOfPages == null) ? 0 : nbrOfPages.hashCode());
		result = prime * result
				+ ((searchCompany == null) ? 0 : searchCompany.hashCode());
		result = prime * result
				+ ((searchComputer == null) ? 0 : searchComputer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wrapper other = (Wrapper) obj;
		if (currentPage == null) {
			if (other.currentPage != null)
				return false;
		} else if (!currentPage.equals(other.currentPage))
			return false;
		if (listCompanies == null) {
			if (other.listCompanies != null)
				return false;
		} else if (!listCompanies.equals(other.listCompanies))
			return false;
		if (listComputers == null) {
			if (other.listComputers != null)
				return false;
		} else if (!listComputers.equals(other.listComputers))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (nbrComputers == null) {
			if (other.nbrComputers != null)
				return false;
		} else if (!nbrComputers.equals(other.nbrComputers))
			return false;
		if (nbrOfPages == null) {
			if (other.nbrOfPages != null)
				return false;
		} else if (!nbrOfPages.equals(other.nbrOfPages))
			return false;
		if (searchCompany == null) {
			if (other.searchCompany != null)
				return false;
		} else if (!searchCompany.equals(other.searchCompany))
			return false;
		if (searchComputer == null) {
			if (other.searchComputer != null)
				return false;
		} else if (!searchComputer.equals(other.searchComputer))
			return false;
		return true;
	}

}