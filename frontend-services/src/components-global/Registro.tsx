import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/registro.css';

interface User {
    username: string;
    email: string;
    senha: string;
    confirmarSenha: string;
    privacyPolicy: boolean;
    pushNotification: boolean;
}

export const Registro: React.FC = () => {
     const navigate = useNavigate();

    const [user, setUser] = useState<User>({
        username: '',
        email: '',
        senha: '',
        confirmarSenha: '',
        privacyPolicy: false,
        pushNotification: false,
    });


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (user.senha !== user.confirmarSenha) {
            alert('Senhas não conferem');
            return;
        }

        try {
            await axios.post('http://localhost:8080/auth/singup/usuario', user);

            alert('Usuário cadastrado com sucesso');
            navigate('/login');
        } catch (error: any) {
            console.error('Error during user registration:', error);
            alert('Erro ao cadastrar usuário');
        }
    };
    return (
        <div className="container">
            <h1>Registre-se</h1>
            <form onSubmit={handleSubmit} className="form-global">
                <input
                    placeholder="Usuário"
                    value={user.username}
                    onChange={(e) => setUser({ ...user, username: e.target.value })}
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={user.email}
                    onChange={(e) => setUser({ ...user, email: e.target.value })}
                />
                <input
                    type="password"
                    placeholder="Senha"
                    value={user.senha}
                    onChange={(e) => setUser({ ...user, senha: e.target.value })}
                />
                <input
                    type="password"
                    placeholder="Confirmar Senha"
                    value={user.confirmarSenha}
                    onChange={(e) => setUser({ ...user, confirmarSenha: e.target.value })}
                />
                <br />

                <div className='label-content'>
                    <input
                        className='input-checkbox'
                        type="checkbox"
                        checked={user.privacyPolicy}
                        onChange={(e) => setUser({ ...user, privacyPolicy: e.target.checked })}
                    />
                    <p className='checkbox-paragrafo'>Aceito a política de privacidade</p>

                    <input
                        className='input-checkbox'
                        type="checkbox"
                        checked={user.pushNotification}
                        onChange={(e) => setUser({ ...user, pushNotification: e.target.checked })}
                    />
                    <p className='checkbox-paragrafo'>Autorizar notificações push (opcional)</p>
                </div>
                <div className='button-content'>
                    <button type="submit">Enviar</button>
                    <button>Limpar</button>
                    <button type="button" onClick={() => navigate('/login')}>Já sou usuario</button>
                </div>
            </form>
        </div>
    );
};

