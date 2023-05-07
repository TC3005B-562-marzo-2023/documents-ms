package com.driveai.documentsms.client;

import com.driveai.documentsms.dto.UserDealershipDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name="users-ms", url="http://localhost:8081")
public interface UserClient {
    // Get the other services endpoints
    @GetMapping("/v1/user/findUserByEmail/{email}")
    public UserDealershipDto findUserByEmail(@PathVariable String email);
}
