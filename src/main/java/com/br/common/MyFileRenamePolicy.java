package com.br.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	
	
	//원본 파일을 전달받아서 파일명 수정작업 후 수정된 파일을 반환시켜주는 메소드
	@Override
	public File rename(File originFile) {
		//원본파일명으로 확장자 알아내기
		String originName = originFile.getName();
		
		//수정파일명("2023011211502012345.jpg")
		// 파일 업로드 시간 (연월일시분초)+5자리랜덤값(10000~99999)+원본파일 확장자
		
		//1) 파일업로드시간
		// SimpleDateFormat는 매개변수로 포멧 넣고 .format(Date객체) 메소드하면 문자열로 포멧 반환
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		//2) 5자리 랜덤값
		int ranNum = (int)(Math.random()*90000+10000);
		//3) 원본파일 확장자
		// substring(.의 인덱스) 단!! 뒤에서부터 찾아야한다(종종 이름에 .이 붙는 경우가 있음)
		String ext = originName.substring(originName.lastIndexOf("."));
		String changeName = currentTime + ranNum + ext;
		
		//파일객체리턴하기
		return new File(originFile.getParent(), changeName);
	}
	

}
