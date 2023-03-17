package 전에하던것;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ClientRepository {
	private static ClientRepository repository = new ClientRepository();

	private ClientRepository() {
	}

	public static ClientRepository getInstance() {
		return repository;
	}

	Scanner sc = new Scanner(System.in);
	private Map<String, ClientDTO> cMap = new HashMap<>();
	List<BreakdownDTO> bList = new ArrayList<>();

	public boolean save(String account, ClientDTO clientDTO, Long balance) {
		if (cMap.put(account, clientDTO) == null) {
			BreakdownDTO breakdownDTO = new BreakdownDTO();
			breakdownDTO.setAccount(account);
			breakdownDTO.setDivision("초기입금");
			breakdownDTO.setDealMoney(balance);
			breakdownDTO.setTotalMoney(balance);
			bList.add(breakdownDTO);
			return true;
		} else {
			return false;
		}
	}

	public Map<String, ClientDTO> findAll() {
		return cMap;
	}

	public boolean login(String loginID, String loginPassword) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(loginID) && cMap.get(key).getPassword().equals(loginPassword)) {
				return true;
			}
		}
		return false;
	}

	public boolean delete(String loginId, String loginPw) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(loginId) && cMap.get(key).getPassword().equals(loginPw)) {
				cMap.remove(key);
				return true;
			}
		}
		return false;
	}

	public ClientDTO findById(String loginid, String loginPw) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(loginid) && cMap.get(key).getPassword().equals(loginPw)) {
				return cMap.get(key);
			}
		}
		return null;
	}

	public List<BreakdownDTO> breaklist(String Account) {
		List<BreakdownDTO> list = new ArrayList<>();
		for (BreakdownDTO b : bList) {
			if (Account.equals(b.getAccount())) {
				list.add(b);
			}
		}
		return list;
	}

	/*
	 * public boolean deposit(String account, long addMoney) { for (String key :
	 * cMap.keySet()) { if (cMap.get(key).getAccount().equals(account)) {
	 * cMap.get(key).setBalance(cMap.get(key).getBalance() + addMoney); BreakdownDTO
	 * breakdownDTO = new BreakdownDTO();
	 * breakdownDTO.setAccount(cMap.get(key).getAccount());
	 * breakdownDTO.setDealMoney(addMoney); breakdownDTO.setDivision("입금");
	 * breakdownDTO.setTotalMoney(cMap.get(key).getBalance());
	 * bList.add(breakdownDTO); return true; }
	 * 
	 * } return false; }
	 */
	public boolean select(String menu, String account) {
		System.out.print(menu + "할 금액 입력>");
		long money = sc.nextLong();
		boolean ok = false;
		if (menu == "출금") {
			money = money * -1;
		}
		if (ok) {
			for (String key : cMap.keySet()) {
				if (cMap.get(key).getAccount().equals(account)) {
					cMap.get(key).setBalance(cMap.get(key).getBalance() + money);
					repository.breakdown(menu, account, money, cMap.get(key).getBalance());
					return true;
				} else {
					return false;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public boolean du(String menu, String account) {
		if (repository.select(menu, account)) {
			return true;
		} else {
			System.out.println("실패");
			return false;
		}
	}

	public void breakdown(String menu, String account, long money, long balance) {
		BreakdownDTO breakdownDTO = new BreakdownDTO();
		breakdownDTO.setAccount(account);
		breakdownDTO.setDealMoney(money);
		breakdownDTO.setDivision(menu);
		breakdownDTO.setTotalMoney(balance);
		bList.add(breakdownDTO);

	}

	/*
	 * public boolean withdraw(String account, long withdrawMoney) { for (String key
	 * : cMap.keySet()) { if (cMap.get(key).getAccount().equals(account)) { if
	 * (cMap.get(key).getBalance() >= withdrawMoney) {
	 * cMap.get(key).setBalance(cMap.get(key).getBalance() - withdrawMoney);
	 * BreakdownDTO breakdownDTO = new BreakdownDTO();
	 * breakdownDTO.setAccount(cMap.get(key).getAccount());
	 * breakdownDTO.setDealMoney(withdrawMoney); breakdownDTO.setDivision("출금");
	 * breakdownDTO.setTotalMoney(cMap.get(key).getBalance());
	 * bList.add(breakdownDTO); return true; } return false; } } return false; }
	 */
	public int du(int num) {
		if (num == 1) {
			return 1;
		} else if (num == 2) {
			return 2;
		} else {
			System.out.println("다시입력");
			return 3;

		}
	}

	/*
	 * public void deposit() { String account = repository.getAccount(loginId,
	 * loginPw); if (account == null) { System.out.println("로그인 오류"); } else {
	 * System.out.print("입금할 금액 입력> "); long addMoney = sc.nextLong(); if
	 * (repository.deposit(account, addMoney)) { System.out.println("입금 완료"); } else
	 * { System.out.println("입금 오류"); } }
	 */

	public boolean transferAccount(String addAccount) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getAccount().equals(addAccount)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public String getAccount(String loginId, String loginPw) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(loginId) && cMap.get(key).getPassword().equals(loginPw)) {
				String c = cMap.get(key).getAccount();
				return c;
			}
		}
		return null;
	}

	public boolean update(String account, String updatePassword) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getAccount().equals(account)) {
				cMap.get(key).setPassword(updatePassword);
				return true;
			}
		}
		return false;
	}

}
