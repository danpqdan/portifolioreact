import { useState, useEffect } from "react";

const useViewer = () => {
  const [count, setCount] = useState(null);

  useEffect(() => {
    const sendViewerData = async () => {
      try {
        // Captura o User-Agent
        const userAgent = navigator.userAgent;
        
        // Captura o IP via um serviço externo
        const ipResponse = await fetch("https://api64.ipify.org?format=json");
        const ipData = await ipResponse.json();
        const ip = ipData.ip;

        // Envia os dados via POST para '/viewer'
        await fetch("http://localhost:8080/api/viewer", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ ip, userAgent }),
        });

        // Busca a contagem de acessos via GET
        const countResponse = await fetch("http://localhost:8080/api/viewer");
        const countData = await countResponse.json();
        setCount(countData);
      } catch (error) {
        console.error("Erro ao capturar viewer:", error);
      }
    };

    sendViewerData();
  }, []);

  return count;
};

export default useViewer;
