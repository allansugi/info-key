import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import { NavBar } from './NavBar';
import PasswordVault from './PasswordVault';
import Login from './Login/Login';
import Home from './home/Home';
import React from 'react';
import { loginContext } from './types/Context';

function App() {
  const loginContext = React.createContext<loginContext | null>(null);
  const [user, setUser] = React.useState('');

  return (
    <loginContext.Provider value={{user, setUser}}>
      <BrowserRouter>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path='/login' element={<Login />} />
          <Route path='/passwords' element={<PasswordVault />} />
        </Routes>
      </BrowserRouter>
    </loginContext.Provider>
    
  )
}

export default App
