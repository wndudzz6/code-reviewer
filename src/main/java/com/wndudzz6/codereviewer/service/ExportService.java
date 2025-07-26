package com.wndudzz6.codereviewer.service;

import com.wndudzz6.codereviewer.dto.ReviewSubmissionDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ExportService {
    ByteArrayOutputStream exportDataToStream(List<ReviewSubmissionDTO> data) throws IOException; //데이터 내보내기
}
