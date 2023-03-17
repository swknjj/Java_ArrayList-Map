package 전에하던것;

import java.util.Scanner;

public class ClientMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ClientService service = ClientService.getInstance(); // 싱글톤 getInstance
		boolean loginOk = false;

		while (true) {
			System.out.println("=====인터넷뱅킹=====");
			if (loginOk) {
				System.out.println("1.입출금조회 2.입금 3.출금 4.계좌이체 5.비밀번호수정 6.회원탈퇴 7.로그아웃 0.종료");
			} else {
				System.out.println("1.회원가입 2.로그인 3.리스트 0.종료");
			}
			System.out.print("선택> ");
			int menu = sc.nextInt();

			if (menu == 1) {
				if (loginOk) {
					service.findById();
				} else {
					service.save();
				}
			} else if (menu == 2) {
				if (loginOk) {
					service.deposit();
				} else {
					service.login();
					loginOk = true;
				}
			} else if (menu == 3) {
				if (loginOk) {
					service.withdraw();
				} else {
					service.findAll();
				}
			} else if (menu == 4 && loginOk) {
					service.transferCheck();
			} else if (menu == 5 && loginOk) {
					service.update();
			} else if (menu == 6 && loginOk) {
				service.delete();
				loginOk = false;
			} else if (menu == 7 && loginOk) {
				System.out.println("로그아웃");
				loginOk = false;
			} else if (menu == 0 && loginOk) {
				break;
			} else {
				System.out.println("다시입력");
			}
			System.out.println();
		}
		System.out.println("프로그램 종료");
	}

}
