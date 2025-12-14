package com.Library.Library.controller;

import lombok.Data;

@Data
public class LoanRequest {
    private Long bookId;
    private Long memberId;
    private Long staffId;
}