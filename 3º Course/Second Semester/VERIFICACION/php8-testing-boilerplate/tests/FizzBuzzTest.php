<?php

namespace Deg540\PHPTestingBoilerplate\Test;

use Deg540\PHPTestingBoilerplate\FizzBuzz;
use PHPUnit\Framework\TestCase;

class FizzBuzzTest extends TestCase
{
    private FizzBuzz $fizzBuzz;

    protected function setUp(): void
    {
        parent::setUp();

        $this->fizzBuzz = new FizzBuzz();
    }

    /**
     * @test
     */
    public function givenOneNumberReturnsThatNumber(): void
    {
        $fizzBuzzResult = $this->fizzBuzz->execute(1);

        $this->assertEquals(1, $fizzBuzzResult);
    }

    /**
     * @test
     */
    public function given3ReturnsTrue(): void
    {
        $isFizz = $this->fizzBuzz->isFizz(3);

        $this->assertTrue($isFizz);
    }

    /**
     * @test
     */
    public function givenANumberDivisibleByThreeReturnsFizz(): void
    {
        $isFizz = $this->fizzBuzz->execute(3);

        $this->assertEquals("Fizz", $isFizz);
    }

    /**
     * @test
     */
    public function givenANumberDivisibleByFiveReturnsBuzz(): void
    {
        $isFizz = $this->fizzBuzz->execute(5);

        $this->assertEquals("Buzz", $isFizz);
    }

    /**
     * @test
     */
    public function givenANumberDivisibleByThreeAndFiveReturnsFizzBuzz(): void
    {
        $isFizz = $this->fizzBuzz->execute(15);

        $this->assertEquals("FizzBuzz", $isFizz);
    }
}