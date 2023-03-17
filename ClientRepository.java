package 전에하던것;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository {
	private static ClientRepository repository = new ClientRepository();
	private Map<String, ClientDTO> cMap = new HashMap<>();

	public static ClientRepository getInstance() {
		return repository;
	}

	List<BreakdownDTO> bList = new ArrayList<>();

	public boolean save(String account, ClientDTO clientDTO, Long balance) {
		ClientDTO result = cMap.put(account, clientDTO);
		if (result == null) {
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

	public boolean deposit(String account, long addMoney) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getAccount().equals(account)) {
				cMap.get(key).setBalance(cMap.get(key).getBalance() + addMoney);
				BreakdownDTO breakdownDTO = new BreakdownDTO();
				breakdownDTO.setAccount(cMap.get(key).getAccount());
				breakdownDTO.setDealMoney(addMoney);
				breakdownDTO.setDivision("입금");
				breakdownDTO.setTotalMoney(cMap.get(key).getBalance() + addMoney);
//				cMap.put(cMap.get(key),cMap.get(key).getBalance());
				bList.add(breakdownDTO);
				return true;
			}

		}
		return false;
	}

	public boolean withdraw(String account, long withdrawMoney) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getAccount().equals(account)) {
				if(cMap.get(key).getBalance()>= withdrawMoney) {
				cMap.get(key).setBalance(cMap.get(key).getBalance() - withdrawMoney);
				BreakdownDTO breakdownDTO = new BreakdownDTO();
				breakdownDTO.setAccount(cMap.get(key).getAccount());
				breakdownDTO.setDealMoney(withdrawMoney);
				breakdownDTO.setDivision("출금");
				breakdownDTO.setTotalMoney(cMap.get(key).getBalance());
				bList.add(breakdownDTO);
				return true;
				}
				return false;
			}
		}
		return false;
	}

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
	public String getAccount (String loginId , String loginPw) {
		for(String key : cMap.keySet()) {
			if(cMap.get(key).getId().equals(loginId) && cMap.get(key).getPassword().equals(loginPw)) {
				String c = cMap.get(key).getAccount();
				return c;
			}
		}
		return null;
	}
	public boolean update(String account , String updatePassword) {
		for(String key : cMap.keySet()) {
			if(cMap.get(key).getAccount().equals(account)) {
				cMap.get(key).setPassword(updatePassword);
				return true;
			}
		}
		return false;
	}
}
