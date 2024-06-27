<?php

namespace Deg540\PHPTestingBoilerplate;

class FizzBuzz
{
    const FIZZ_BUZZ = "FizzBuzz";
    const BUZZ = "Buzz";
    const FIZZ = "Fizz";
    private const FIZZ_NUMBER = 3;
    private const BUZZ_NUMBER = 5;

    public function execute(int $number)
    {
        if ($this->isFizzBuzz($number)) {
            return self::FIZZ_BUZZ;
        }
        if ($this->isBuzz($number)) {
            return self::BUZZ;
        }
        if ($this->isFizz($number)){
            return self::FIZZ;
        }
        return $number;
    }
    public function isFizz($number):bool{
        return ($this->isNumberDivisibleBy3($number));
    }

    private function isBuzz($number):bool{
        return ($number % self::BUZZ_NUMBER == 0);
    }

    private function isFizzBuzz($number):bool{
        return ($number % self::FIZZ_NUMBER == 0 and $number % self::BUZZ_NUMBER == 0);
    }

    private function isNumberDivisibleBy3 ($number):bool{
        return ($number % self::FIZZ_NUMBER == 0);
    }
}