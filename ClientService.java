package 전에하던것;

import java.util.*;

public class ClientService {
	private static ClientService service = new ClientService();

	// 생성자를 private제한
	private ClientService() {
	}

	// 싱글톤패턴
	public static ClientService getInstance() {
		return service;
	}

	private static Scanner sc = new Scanner(System.in);
	private ClientRepository repository = ClientRepository.getInstance();
	private String loginId = null;
	private String loginPw = null;
	private String loginAcc = null;

	public void save() {
		System.out.print("id> ");
		String id = sc.next();
		System.out.print("pw> ");
		String password = sc.next();
		System.out.print("name>");
		String name = sc.next();
		System.out.print("초기입금금액");
		Long balance = sc.nextLong();
		ClientDTO clientDTO = new ClientDTO(id, password, name, balance);
		if (repository.save(clientDTO.getAccount(), clientDTO, clientDTO.getBalance())) {
			System.out.println("회원가입 완료");
		} else {
			System.out.println("회원가입 실패");
		}

	}

	public void findAll() {
		Map<String, ClientDTO> cMap = repository.findAll();
		List<String> keySet = new ArrayList<>(cMap.keySet());
		keySet.sort(Comparator.naturalOrder());
		System.out.println("계좌\t\t아이디\t비밀번호\t이름\t잔액\t가입날짜");
		System.out.println("===================================================");
		for (String key : keySet) {
			System.out.println(cMap.get(key).toString());
		}
	}

	public void login() {
		System.out.print("login Id> ");
		String loginId2 = sc.next();
		System.out.print("login Password> ");
		String loginPassword2 = sc.next();
		if (repository.login(loginId2, loginPassword2)) {
			loginId = loginId2;
			loginPw = loginPassword2;
			System.out.println("로그인 성공");
		} else {
			System.out.println("로그인 실패");
		}
	}

	public void delete() {
		System.out.print("삭제할 계정의 비밀번호 입력> ");
		String deletePassword = sc.next();
		if (deletePassword.equals(loginPw)) {
			if (repository.delete(loginId, loginPw)) {
				System.out.println("삭제완료");
			} else {
				System.out.println("삭제오류");
			}

		} else {
			System.out.println("비밀번호 오류");
		}
	}

	public void findById() {
		ClientDTO clientDTO = repository.findById(loginId, loginPw);
		if (clientDTO == null) {
			System.out.println("로그인 오류");
		} else {
			List<BreakdownDTO> bList = repository.breaklist(clientDTO.getAccount());
			if (bList.size() == 0) {
				System.out.println("입출금 내역이 없습니다");
			} else {
				System.out.println("입출금내역");
				System.out.println("계좌번호\t\t구분\t거래금액\t잔액\t거래일자");
				System.out.println("===================================================");
				for (BreakdownDTO b : bList) {
					System.out.println(b);
				}
				System.out.println("===================================================");
			}
		}

	}

	public void deposit() {
		String account = repository.getAccount(loginId, loginPw);
		if (account == null) {
			System.out.println("로그인 오류");
		} else {
			System.out.print("입금할 금액 입력> ");
			long addMoney = sc.nextLong();
			if (repository.deposit(account, addMoney)) {
				System.out.println("입금 완료");
			} else {
				System.out.println("입금 오류");
			}
		}

	}

	public void withdraw() {
		String account = repository.getAccount(loginId, loginPw);
		if (account == null) {
			System.out.println("로그인 오류");
		} else {
			System.out.println("출금할 금액 입력");
			long withdrawMoney = sc.nextLong();
			if (repository.withdraw(account, withdrawMoney)) {
				System.out.println("출금 성공");
			} else {
				System.out.println("잔액 부족");

			}
		}
	}

	public void transferCheck() {
		String account = repository.getAccount(loginId, loginPw);
		if (account == null) {
			System.out.println("로그인 오류");
			return;
		}
		System.out.print("계좌이체받을 계좌번호 입력> ");
		String addAccount = sc.next();
		if (repository.transferAccount(addAccount)) {
			System.out.print("계좌이체할 금액 입력> ");
			long transferMoney = sc.nextLong();
			if (repository.withdraw(account, transferMoney)) {
				if (repository.deposit(addAccount, transferMoney)) {
					System.out.println("이체 성공");
				}
			} else {
				System.out.println("이체할 잔액 부족");
			}
		} else {
			System.out.println("계좌번호 오류");
		}

	}
	
	public void update() {
		String account = repository.getAccount(loginId, loginPw);
		if(account == null) {
			System.out.println("로그인 오류");
			return;
		}else {
			System.out.print("수정할 비밀번호> ");
			String updatePassword = sc.next();
			if(repository.update(account,updatePassword)) {
				System.out.println("비밀번호 수정 완료");
			}else {
				System.out.println("수정 오류");
			}
		}
	}
}