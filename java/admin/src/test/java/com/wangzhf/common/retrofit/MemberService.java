package com.wangzhf.common.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MemberService {

    // @FormUrlEncoded
    @POST("/cal/DeAes/")
    public Call<Member> getMember(@Body Member member);

}
