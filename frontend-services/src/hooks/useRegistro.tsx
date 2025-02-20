import { useEffect, useState } from 'react';

interface RegisterResponseDTO {
  message: string;
  success: boolean;
}

const useRegister = () => {
  const [registerData, setRegisterData] = useState<RegisterResponseDTO | null>(null);
  const [error, setError] = useState<string | null>(null);

  const handleRegister = async (userData: { username: string; email: string; password: string }) => {
    try {
      const response = await fetch('http://localhost:8080/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      if (response.status !== 201) {
        const errorText = await response.text();
        throw new Error(`HTTP error! Status: ${response.status}, Message: ${errorText}`);
      }
      const data: RegisterResponseDTO = await response.json();
      setRegisterData(data);
      alert(`Registration successful! Message: ${data.message}`);
      return null
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError('An unknown error occurred');
      }
      setRegisterData(null);
      return error;
    }
  };

  useEffect(() => {
    return () => {
      setRegisterData(null);
      setError(null);
    };
  }, []);

  return { registerData, error, handleRegister };
};

export default useRegister;
