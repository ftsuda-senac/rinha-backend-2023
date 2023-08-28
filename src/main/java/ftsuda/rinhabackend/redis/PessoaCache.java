package ftsuda.rinhabackend.redis;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaCache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String apelido;

}
