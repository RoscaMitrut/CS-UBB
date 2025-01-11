// src/AuthContext.tsx
import React, { createContext, useState, useContext, useEffect } from "react";
import axios from "axios";

interface AuthContextType {
	isAuthenticated: boolean;
	token: string | null;
	login: (username: string, password:string) => Promise<void>;
	logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

type Props = {
  children?:React.ReactNode
};

export const AuthProvider: React.FC<Props> = ({children}) => {
	const [token, setToken] = useState<string | null>(
		localStorage.getItem("token")
	);

	const isAuthenticated = !!token;

  const login = async (username: string, password: string) => {
    try {
      const response = await axios.post("http://127.0.0.1:5000/login", {
        username,
        password,
      });
      const { token } = response.data;
  
      localStorage.setItem("token", token);
      setToken(token);
    } catch (error) {
      console.error("Login failed:", error);
      throw new Error("Invalid credentials");
    }
  };
  

	const logout = () => {
		localStorage.removeItem("token");
		setToken(null);
	};

	useEffect(() => {
		if (token) {
			axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
		} else {
			delete axios.defaults.headers.common["Authorization"];
		}
	}, [token]);

	return (
		<AuthContext.Provider
			value={{ isAuthenticated, token, login, logout }}
		>
			{children}
		</AuthContext.Provider>
	);
};

export const useAuth = () => {
	const context = useContext(AuthContext);
	if (!context) throw new Error("useAuth must be used within AuthProvider");
	return context;
};
