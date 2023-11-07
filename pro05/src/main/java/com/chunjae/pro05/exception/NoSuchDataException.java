package com.chunjae.pro05.exception;

public class NoSuchDataException extends RuntimeException {
    public NoSuchDataException(String message) {
        super(message);
    }
    /*
    NosuchDataException을 만드는 이유
    -> 데이터가 없는 경우 empty로 넘어오지만 JAVA에서는 empty를 확인할 수 있는 방법이 없기 때문에 이를 해결하기 위해

    getUserList : 만약, 테이블이 비어 있다면, 빈 리스트 반환
    getUser     : 일치하는 데이터가 없으면, null로 반환
    updateUser  : update 된 데이터가 없으면, 0 반환
    removeUser  : delete 된 데이터가 없으면, 0 반환
    joinPro     : insert 된 데이터가 없으면, 0 반환
    */

}
