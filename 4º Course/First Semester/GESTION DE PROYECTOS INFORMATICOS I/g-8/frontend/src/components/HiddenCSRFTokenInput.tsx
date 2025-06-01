import React from "react";

const getCSRFToken = () => {
  const dataDiv: HTMLDivElement = document.getElementById(
    "data"
  ) as HTMLDivElement;
  return dataDiv.dataset.csrftoken as string;
};

const HiddenCSRFTokenInput: React.FC = () => {
  const csrfToken = getCSRFToken();
  return <input type="hidden" name="csrfmiddlewaretoken" value={csrfToken} />;
};

export { HiddenCSRFTokenInput, getCSRFToken };
