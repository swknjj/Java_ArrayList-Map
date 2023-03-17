package 전에하던것;

import java.util.*;

public class Util {
	Scanner sc = new Scanner(System.in);
	private ClientRepository repository = ClientRepository.getInstance();
//	private Map<String, ClientDTO> cMap = new HashMap<>();
	List<BreakdownDTO> bList = new ArrayList<>();
	public int numberCheck() {
		int result;
		while (true) {
			if (sc.hasNextInt()) {
				result = sc.nextInt();
				break;
			} else {
				System.out.println("숫자만 입력하세요");
				sc.nextLine();
			}
		}
		return result;
	}
	
	public String duCheck() {
		Map<String, ClientDTO> cMap = repository.findAll();
		String result;
		while (true) {
			result = sc.next();
			boolean find = false;
			for (String key : cMap.keySet()) {
				if (cMap.get(key).getId().equals(result)) {
					System.out.println("중복된 아이디입니다");
					find = true;
					break;
				}
			}
			if (!find) {
				break;
			}
		}
		return result;
	}

}

/*
public String duCheck(List<Shopping>list) {
Scanner sc = new Scanner(System.in);
String result;
while(true) {
	result = sc.next();
	boolean find = false;
	for(Shopping s : list) {
		if(s.getTitle().equals(result)) {
			System.out.println("중복된 아이디 입니다 다시입력>");
			find = true;
			break;
		}
	}
	if(!find) {
		break;
	}
}
return result;
}
*/