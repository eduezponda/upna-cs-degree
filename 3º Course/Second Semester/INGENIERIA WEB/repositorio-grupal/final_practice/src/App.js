import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './Header';
import MainContent from './MainContent';
import Footer from './Footer';
import AboutUs from './AboutUs';
import Blog from './Blog';
import Help from './Help';

const BookRealmImage = () => (
  <div className="bookrealm-image"></div>
);

const App = () => {
  return (
    <Router>
      <div className="app-container">
        <Header />
        <BookRealmImage />
        <Routes>
          <Route path="/" element={<MainContent />} />
          <Route path="/about" element={<AboutUs />} />
          <Route path="/blog" element={<Blog />} />
          <Route path="/help" element={<Help />} />
        </Routes>
        <Footer />
      </div>
    </Router>
  );
};

export default App;


