import React from "react";
import Logo from "../../public/qf_logo.svg";

const QuizForgeLogo: React.FC = () => {
  return (
    <img
      src={Logo}
      className="object-scale-down max-w-20 mx-5"
      alt="Logotipo de QuizForge"
    />
  );
};

export default QuizForgeLogo;
