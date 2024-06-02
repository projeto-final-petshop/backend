## Formativa 1

### Questões

1. Acrescentar na tabela `users` do Banco de Dados o campo numérido (`int`) para incluir o **número da sorte** (`insert`
   e `select` via navegador).
2. Acrescentar na tabela `users` do Banco de Dados o campo texto (`varchar(50)`) para incluir o **cor favorita
   ** (`insert` e `select` via navegador).
3. Acrescentar na tabela `users` do Banco de Dados o campo `float` (`float`) para incluir o **valor do ingresso
   ** (`insert` e `select` via navegador).

### Explicação

Para adicionar novos campos à tabela users no banco de dados MySQL e para poder inserir e selecionar esses valores
através de um navegador, você precisará seguir alguns passos:

* Para alterar a estrutura da tabela no Banco de Dados:
    * Use comandos `SQL ALTER TABLE` para adicionar os novos campos.

* Atualizar a Entidade do Usuário no seu Projeto Spring Boot:
    * Adicione os novos campos na classe `User`.

* Adicionar Lógica no Serviço e Controlador para Manipular os Novos Campos
    * Atualize os métodos de inserção e seleção para incluir os novos campos.

### Resolução

**Passo 1: Alterar a Estrutura da Tabela no Banco de Dados**

Execute os seguintes comandos SQL no seu banco de dados MySQL para adicionar os novos campos à tabela users:

```mysql
ALTER TABLE users
    ADD COLUMN lucky_number   INT,
    ADD COLUMN favorite_color VARCHAR(50),
    ADD COLUMN ticket_price   FLOAT;
```

**Passo 2: Atualizar a Entidade `User`**

Modifique a classe `User` para incluir os novos campos:

```java

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_cpf", columnNames = "cpf")
})
@Entity
public class User {

    // CAMPOS JÁ EXISTENTE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("userId")
    private Long id;

    // OUTROS CAMPOS JÁ IMPLEMENTADOS

    // NOVOS CAMPOS: getters e setters já implementados (utilizando anotações acima)
    private Integer luckyNumber;
    private String favoriteColor;
    private Float ticketPrice;


}

```

**Passo 3: Atualizar a Classe `UserRequest`**

Modifique a classe `UserRequest` para incluir os novos campos:

```java

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // OUTROS CAMPOS JÁ IMPLEMENTADOS

    // NOVOS CAMPOS: getters e setters já implementados (utilizando anotações acima)
    private Integer luckyNumber;
    private String favoriteColor;
    private Float ticketPrice;


}

```

**Passo 4: Atualizar o Serviço de Usuário**

Modifique os métodos no seu serviço de usuário para lidar com os novos campos. Por exemplo, para inserir novos valores:

```java

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final MessageUtil messageUtil;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(UserRequest input) {

        log.info("Iniciando processo de cadastro para o email: {}", input.getEmail());

        if (userRepository.existsByEmail(input.getEmail())) {
            log.error("Erro ao cadastrar usuário: email {} já está em uso", input.getEmail());
            throw new EmailAlreadyExistsException("Email já está em uso");
        }

        if (userRepository.existsByCpf(input.getCpf())) {
            log.error("Erro ao cadastrar usuário: CPF {} já está em uso", input.getCpf());
            throw new CpfAlreadyExistsException("CPF já está em uso");
        }

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            log.error("Erro ao cadastrar usuário: role USER não encontrada");
            throw new RoleNotFoundException("Role USER não encontrada");
        }

        // NOVOS CAMPOS IMPLEMENTADOS
        User user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .active(true)
                .cpf(input.getCpf())
                .phoneNumber(input.getPhoneNumber())
                .luckyNumber(input.getLuckyNumber()) // NOVO CAMPO
                .favoriteColor(input.getFavoriteColor()) // NOVO CAMPO
                .ticketPrice(input.getTicketPrice()) // NOVO CAMPO
                .role(optionalRole.get())
                .build();

        User savedUser = userRepository.save(user);
        log.info("Usuário cadastrado com sucesso: {}", savedUser.getId());
        return savedUser;
    }

    // OUTROS MÉTODOS JÁ IMPLEMENTADOS

}
```

* **Observação:** O `AuthenticationController` **NÃO** deve ser modificado.

**Passo 5: Atualizar o Template HTML**

Adicione os novos campos ao formulário de cadastro no template HTML:

```html

<body>
<div class="main">
    <input type="checkbox" id="chk" aria-hidden="true">

    <div class="signup">
        <form [formGroup]="cadastroForm" (ngSubmit)="cadastrar()">
            <label for="chk" (click)="esqueciSenha(false)" aria-hidden="true">Cadastro</label>
            <input type="text" formControlName="name" placeholder="Nome" required>
            <input type="email" formControlName="email" placeholder="Email" required>
            <div *ngIf="cadastroForm.get('email')?.touched || cadastroForm.get('email')?.dirty">
                <div *ngIf="cadastroForm.get('email')?.errors?.['email']">
                    <p class="error-message">
                        Por favor, insira um endereço de e-mail válido.</p>
                </div>
            </div>
            <input type="text" formControlName="cpf" appCpfFormat placeholder="CPF">
            <input type="tel" formControlName="phoneNumber" placeholder="Número de Telefone" required>
            <input type="password" formControlName="password" placeholder="Senha" required>
            <div style="padding-top: 10px !important; padding-bottom: 10px;">
                <p *ngIf="cadastroForm.get('password')?.value?.length > 0 && cadastroForm.get('password')?.errors?.['passwordStrength']"
                   class="error-message">
                    Para sua segurança, sua senha deve conter letras maiúsculas, minúsculas, números e caracteres
                    especiais.</p>
            </div>

            <!-- Novos campos -->
            <input type="number" formControlName="luckyNumber" placeholder="Número da Sorte">
            <input type="text" formControlName="favoriteColor" placeholder="Cor Favorita">
            <input type="number" step="0.01" formControlName="ticketPrice" placeholder="Valor do Ingresso">

            <button style="margin-bottom: 10px !important;" [disabled]="!cadastroForm.valid" type="submit">Realizar
                cadastro
            </button>
        </form>
    </div>

    <div class="login">
        <form [formGroup]="loginForm" (ngSubmit)="login()">
            <div *ngIf="!isForgetPassword">
                <label style="padding-top: 40px !important;" for="chk" aria-hidden="true"><p>Login</p></label>
                <input type="email" formControlName="email" placeholder="Usuário" required="true">
                <input type="password" formControlName="password" placeholder="Senha" required="">
                <button type="submit">Login</button>
                <a class="esqueciSenha" href="#" (click)="esqueciSenha(true); $event.preventDefault()">Esqueci minha
                    senha</a>
            </div>

            <div *ngIf="isForgetPassword">
                <label *ngIf="isForgetPassword" style="padding-top: 40px !important;" for="chk" aria-hidden="true"><p>
                    Esqueci a senha</p></label>
                <p style="padding: 30px;">Digite seu CPF e enviaremos um email para que você possa redefinir sua
                    senha</p>
                <input type="text" formControlName="documentNumber" appCpfFormat placeholder="CPF">
                <a class="esqueciSenha" href="#" (click)="esqueciSenha(false); $event.preventDefault()">Realizar
                    login</a>
            </div>
        </form>
    </div>
</div>
</body>

```

**Passo 6: Atualizar o Componente TypeScript**

Adicione os novos campos ao `FormGroup` em `LoginComponent`:

```typescript
import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {UsuarioService} from '../../services/usuario.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    cadastroForm: FormGroup;
    loginForm: FormGroup;
    isForgetPassword!: boolean;

    constructor(private usuarioService: UsuarioService, private router: Router) {
        this.cadastroForm = new FormGroup({
            name: new FormControl('', [Validators.required]),
            email: new FormControl('', [Validators.required, Validators.email]),
            password: new FormControl('', [Validators.required, this.passwordStrengthValidator()]),
            cpf: new FormControl('', [Validators.required]),
            phoneNumber: new FormControl('', [Validators.required]),

            // Novos campos
            luckyNumber: new FormControl('', [Validators.required]),
            favoriteColor: new FormControl('', [Validators.required]),
            ticketPrice: new FormControl('', [Validators.required])
        });

        this.loginForm = new FormGroup({
            email: new FormControl('', [Validators.required]),
            password: new FormControl('', [Validators.required])
        });
    }

    ngOnInit(): void {
    }

    cadastrar() {
        if (this.cadastroForm.valid) {
            this.usuarioService.registerUser(this.cadastroForm.value).subscribe({
                next: (response) => {
                    console.log('Usuário cadastrado com sucesso!', response);
                },
                error: (error) => console.error('Erro ao cadastrar usuário', error)
            });
        }
    }

    login() {
        if (this.loginForm.valid) {
            const userData = {
                email: this.loginForm.value.email,
                password: this.loginForm.value.password
            };

            this.usuarioService.loginUser(userData).subscribe({
                next: (response) => {
                    if (response.token) {
                        localStorage.setItem('token', response.token);
                        this.router.navigate(['/perfil-cliente']);
                    }
                    console.log('Login realizado com sucesso!', response);
                },
                error: (error) => console.error('Erro ao realizar login', error)
            });
        }
    }

    esqueciSenha(isForgetPassword: boolean) {
        this.isForgetPassword = isForgetPassword;
    }

    passwordStrengthValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value || '';
            const hasUpperCase = /[A-Z]+/.test(value);
            const hasLowerCase = /[a-z]+/.test(value);
            const hasNumeric = /[0-9]+/.test(value);
            const hasSpecial = /[\W_]+/.test(value);
            const isValidLength = value.length >= 8;
            if (hasUpperCase && hasLowerCase && hasNumeric && hasSpecial && isValidLength) {
                return null;
            }
            return {
                passwordStrength: 'Password must be at least 8 characters long, include at least one uppercase letter, one number, and one special character.'
            };
        };
    }
}

```

**Passo 7: Atualizar o Serviço de Usuário**

Certifique-se de que o serviço de usuário (`UsuarioService`) está preparado para lidar com os novos campos:

```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class UsuarioService {

    private baseUrl = 'http://localhost:8888/api/v1/users';
    private registerUrl = `${this.baseUrl}/register`;
    private loginUrl = 'http://localhost:8888/api/v1/auth/login';

    constructor(private http: HttpClient) { }

    registerUser(userData: any): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        return this.http.post('http://localhost:8888/api/v1/auth/signup', userData, { headers });
    }

    loginUser(userData: any): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        return this.http.post(this.loginUrl, userData, { headers }).pipe(
            tap((response: any) => {
                localStorage.setItem('token', response.token);
            })
        );
    }

    updateUser(userId: number, userData: any): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        return this.http.put(`${this.baseUrl}/update`, userData, { headers });
    }

    getUserById(): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.get(`${this.baseUrl}/me`, { headers });
    }

    deleteUser(userId: number): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
        return this.http.delete(`${this.baseUrl}/delete`, { headers });
    }
}

```