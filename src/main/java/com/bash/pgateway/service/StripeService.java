package com.bash.pgateway.service;

import com.bash.pgateway.dto.ProductRequest;
import com.bash.pgateway.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
//    Stripe -API
//    ProductName, amount, quantity, currency
//    Returns sessionId and url

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public StripeResponse checkOut(ProductRequest productRequest) {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.name()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.currency()==null?"USD":productRequest.currency())
                .setUnitAmount(productRequest.amount())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.quantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();

        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            return StripeResponse.builder()
                    .status("FAILED")
                    .message("Payment session could not be created")
                    .build();
        }
        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionURL(session.getUrl())
                .build();
    }
}