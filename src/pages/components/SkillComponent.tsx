import { FaJava } from "react-icons/fa";
import { GiTalk } from "react-icons/gi";
import { IoLogoReact } from "react-icons/io5";
import { SiDatabricks } from "react-icons/si";
import CardSkill from "../../hook/CardSkill";


import "../css/style.module.skill.css";

const SkillComponent = () => {
    return (
        <div id="container-skill">
            <CardSkill childrenBack={[
                <>
                    <ul id="container-skill-list">
                        <li>Conversar é sempre a melhor opção</li>
                        <li>Observação e proatividade é importante</li>
                        <li>Buscar avaliação, construir sem descontruir</li>
                        <li>Analise critíca</li>
                        <li>Busque fluencia em outros idiomas</li>
                    </ul>
                </  >

            ]} childrenFront={[
                <>
                    <GiTalk fontSize={50} color='blue' />
                    <span>Sobre</span>
                </>
            ]}>
            </CardSkill>
            <CardSkill childrenBack={[
                <>
                    <ul id="container-skill-list">
                        <li>POO</li>
                        <li>Estruturas de Dados</li>
                        <li>Algoritmos</li>
                        <li>Coleções</li>
                        <li>I/O</li>
                        <li>Servelets</li>
                        <li>Ecosistema Spring</li>
                    </ul>
                </>
            ]} childrenFront={[
                <>
                    <FaJava fontSize={50} color='blue' />
                    <span>Java</span>
                </>
            ]}>
            </CardSkill>
            <CardSkill childrenBack={[
                <>
                    <ul id="container-skill-list">
                        <li>Interfaces interativas</li>
                        <li>Modularização & Componentização</li>
                        <li>Hooks e Gerenciamento de estado</li>
                        <li>Redux</li>
                        <li>Formularios e eventos</li>
                    </ul>
                </>
            ]} childrenFront={[
                <>
                    <IoLogoReact fontSize={50} color='blue' />
                    <span>React</span>
                </>
            ]}>
            </CardSkill>
            <CardSkill childrenBack={[
                <>
                    <ul id="container-skill-list">
                        <li>SQL e NoSQL</li>
                        <li>Modelo de dados</li>
                        <li>Queries personalizadas</li>
                        <li>Integridade nos dados: FK, PK</li>
                        <li>Relacionamento de dados</li>
                        <li>Modelagem de dados</li>
                    </ul>
                </>
            ]} childrenFront={[
                <>
                    <SiDatabricks fontSize={50} color='blue' />
                    <span>Banco de dados</span>
                </>

            ]}>
            </CardSkill>

        </div>
    );
}

export default SkillComponent;
