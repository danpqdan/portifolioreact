import React from 'react';

const SobreComponent = () => {
    return (
        <div style={{
            width: "100%",
            height: "100%",
            backgroundColor: 'rgba(0, 0, 0, 0.596)'
        }}>
            <div style={{ color: 'white', justifyContent: 'center', alignItems: 'center', display: 'flex', flexDirection: 'row', flexWrap: 'wrap' }}>
                <h1>Sobre mim</h1>
                <p style={{
                    textAlign: 'justify',
                    margin: '4px 12px'
                }}>
                    Olá seja bem vindo à essa LandPage, gostaria de me apresentar.
                    Me chamo Daniel Santos, sou nascido no estado de Espirito Santo ES na cidade de Vila Velha

                    , tenho um garotinho de 5 anos, atualmente curso desenvolvimento de sistemas na FAM mas minha

                    paixão pela técnologia é muito anterior.

                    Comecei a me interessar pela área lembro que tinha por volta de 12(doze) anos de idade,

                    meu computador na época era muito ruim e tinha um problema crônico no HD então para resolver o problema

                    de tela azul que era constante aprendi a fazer manutenções como formatação, limpeza, recuperação e afins

                    relacionados ao Hardware, até o dia que ele deixou de funcionar e não consegui comprar um novo.

                    Logo depois aos 16(dezesseis) anos fiz um curso de Web Desgner aprendendo a trabalhar com imagens e videos,

                    realizar tratamento com fundos verdes, transições em geral, me divertia muito com o curso. Mas ainda não era

                    o que queria realizar como tarefa.

                    Aos 23(vinte e três) anos relembrei sobre o quanto gostaria de trabalhar com tecnologia então guardei um dinheiro

                    e enfim comprei meu primeiro computador, aprendi um pouco de Node, um pouco de Python, mas o Java me interessou

                    pela sua facilidade de integração com aparelhos eletronicos(IoT). Mas ainda assim acho super interessante

                    as bibliotecas de machine learning e os grandes avanços na área de algoritimos e buscas como o Go ou o Vue
                </p>
            </div>


        </div>
    );
}

export default SobreComponent;
