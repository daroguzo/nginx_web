package kr.co.direa.nginx_web.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class OpenPort {
    String ip;

    long port;
}
