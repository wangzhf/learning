package com.wangzhf.common.retrofit;

import okhttp3.OkHttpClient;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Map;

public class RetrofitTest {

    String url = "http://127.0.0.1";

    @Test
    public void testPost() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();
        Member member = new Member();
        member.setName("vUsGkFrEzKIAizSf2RT6OhsvPjMdouTXekAJrVBiPfM=");
        MemberService service = retrofit.create(MemberService.class);
        Map<String, Object> params = new HashedMap<>();
        params.put("name", member.getName());
        Call<Member> call = service.getMember(member);
        Response<Member> response = call.execute();
        System.out.println(response.raw().code());
        System.out.println(response.body().getName());
    }

}

class Member {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
            "name='" + name + '\'' +
            '}';
    }
}
