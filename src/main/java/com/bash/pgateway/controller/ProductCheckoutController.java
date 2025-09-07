package com.bash.pgateway.controller;

import com.bash.pgateway.dto.ProductRequest;
import com.bash.pgateway.dto.StripeResponse;
import com.bash.pgateway.service.StripeService;
import com.stripe.net.StripeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductCheckoutController {
    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkOut(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }

}
