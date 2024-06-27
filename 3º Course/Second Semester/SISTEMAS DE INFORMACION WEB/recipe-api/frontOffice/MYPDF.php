<?php
    require_once '../vendor/autoload.php';

    class MYPDF extends TCPDF {

        public function __construct($orientation = 'P', $unit = 'mm', $format = 'A4', $unicode = true, $encoding = 'UTF-8', $diskcache = false, $pdfa = false) {
            parent::__construct($orientation, $unit, $format, $unicode, $encoding, $diskcache, $pdfa);
            define('K_PATH_IMAGES_', $_SERVER['DOCUMENT_ROOT'].'Trabajo Final/imagenes/');
        }

        public function Header() {
            $image_file = K_PATH_IMAGES_.'logoMazapan.png';
            $this->Image($image_file, 160, 10, 25, '', 'PNG', '', 'T', false, 300, '', false, false, 0, false, false, false);

            $this->Ln(5); 

            $this->SetFont('helvetica', 'I', 8);
            $this->Cell(0, 10, "Date: ".date('Y-m-d H:i:s'), 0, false, 'L', 0, '', 0, false, 'T', 'T');

            $this->Ln(5); 

            $this->SetFont('helvetica', '', 10);
            $this->Cell(0, 10, 'Company: Mazapan Corporate', 0, 1, 'L', 0, '', 0, false, 'T', 'M');
        }

        public function Footer() {
            $this->SetY(-15);
            $this->SetFont('helvetica', 'I', 8);
            $this->Cell(0, 10, 'Mazapan Corporate', 0, false, 'C', 0, '', 0, false, 'T', 'M');
        }
    }
?>
