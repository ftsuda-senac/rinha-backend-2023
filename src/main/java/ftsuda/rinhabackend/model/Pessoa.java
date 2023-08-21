package ftsuda.rinhabackend.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pessoa implements Persistable<UUID> {

    @Id
    private UUID id;

    @NotEmpty
    @Size(max = 32)
    private String apelido;

    @NotEmpty
    @Size(max = 100)
    private String nome;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;

    @JsonIgnore
    private String stackDb;

    @Transient
    @PessoaStack(maxChars = 32)
    private List<String> stack;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }

}
